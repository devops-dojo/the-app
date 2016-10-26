package io.github.zutherb.appstash.shop.service.product.impl

import io.github.zutherb.appstash.shop.service.product.api.{ConfigurationModule, ProductRepositoryModule}
import io.github.zutherb.appstash.shop.service.product.model.{Product, ProductQuery}
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import spray.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ShopProductRepositoryModule extends ProductRepositoryModule {
  self: ShopDBModule with ConfigurationModule =>

  val db = MongoDB().get()

  override def ProductRepository(): ProductRepository = new ProductRepository

  class ProductRepository extends ProductRepositoryLike {

    val productCollection = db.collection("product")

    override def findAll(): Future[List[Product]] = productCollection.find(BSONDocument.empty)
        .cursor[Product]
        .collect[List]()

    override def findByQuery(query: ProductQuery): Future[List[Product]] = productCollection.find(query)
      .cursor[Product]
      .collect[List]()
  }

}
