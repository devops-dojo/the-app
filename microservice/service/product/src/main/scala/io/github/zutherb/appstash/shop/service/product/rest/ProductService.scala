package io.github.zutherb.appstash.shop.service.product.rest

import akka.actor.{ActorSystem => AkkaActorSystem, Actor}
import akka.util.Helpers.Requiring
import com.typesafe.config.ConfigFactory
import io.github.zutherb.appstash.shop.service.product.Boot
import io.github.zutherb.appstash.shop.service.product.api.{ConfigurationModule, ProductRepositoryModule}
import io.github.zutherb.appstash.shop.service.product.impl.{ShopDBModule, ShopProductRepositoryModule}
import io.github.zutherb.appstash.shop.service.product.model.{SystemConfiguration, ProductQuery}
import spray.http.{DateTime, StatusCodes, HttpResponse, MediaTypes}
import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.httpx.encoding.Gzip
import spray.httpx.marshalling.ToResponseMarshallable
import spray.json.pimpAny
import spray.routing.HttpService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ProductServiceActor extends Actor with ProductService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait ProductService extends HttpService with ConfigurationModule
                                         with ShopDBModule
                                         with ShopProductRepositoryModule {

  override def Configuration() = new Configuration

  class Configuration extends ConfigurationLike{

    override def get(): SystemConfiguration = Boot.systemConfig
  }

  private def getAllProducts = ProductRepository.findAll()

  private def getAllProductsHashcodeAsString : String = getAllProducts.hashCode()+""

  val myRoute =
    path("manifest") {
      get {
        respondWithLastModifiedHeader(config.serverStartedAt) {
          encodeResponse(Gzip) {
            respondWithMediaType(MediaTypes.`text/plain`) {
              complete {
                scala.io.Source.fromInputStream(getClass.getResourceAsStream("/META-INF/MANIFEST.MF")).mkString
              }
            }
          }
        }
      }
    } ~
      path("env") {
        respondWithLastModifiedHeader(config.serverStartedAt) {
          encodeResponse(Gzip) {
            get {
              respondWithMediaType(MediaTypes.`application/json`) {
                complete {
                  config
                }
              }
            }
          }
        }
      } ~
      path("all") {
        conditional(new spray.http.EntityTag(getAllProductsHashcodeAsString, false), DateTime.now){
          get {
            respondWithMediaType(MediaTypes.`application/json`) {
              encodeResponse(Gzip) {
                  complete(getAllProducts)
              }
            }
          }
        }
      } ~
      path("search") {
        get {
          parameter("productType".?, "id".?, "articleId".?, "name".?, "urlname".?) {
            (productType, id, articleId, name, urlname) =>
              respondWithMediaType(MediaTypes.`application/json`) {
                complete {
                  val query: ProductQuery = new ProductQuery(id, productType, articleId, name, urlname)
                  ProductRepository.findByQuery(query)
                }
              }
          }
        }
      } ~
      path("shutdown") {
        get {
          complete {
            System.exit(0)
            "ok"
          }
        }
      } ~
      path("feed") {
        get {
          respondWithMediaType(MediaTypes.`application/xml`) {
            // XML is marshalled to `text/xml` by default, so we simply override here
            complete {
              val products = getAllProducts
              products map { products =>

                <rss version="2.0" xmlns:g="http://base.google.com/ns/1.0">
                  <channel>
                    <title>Name Ihres Daten-Feeds</title>
                    <link>http://www.example.com</link>
                    <description>Beschreibung des Inhalts</description>{for (product <- products.value) yield
                    <item>
                      <title>
                        {product.name}
                      </title>
                      <link>http://www.example.com/item1-info-page.html</link>
                      <description>
                        {product.description}
                      </description>
                      <g:image_link>http://www.example.com/image1.jpg</g:image_link> <g:price>25</g:price> <g:condition>neu</g:condition> <g:id>1a</g:id>
                    </item>}
                  </channel>
                </rss>
              }
            }
          }
        }
      }
}