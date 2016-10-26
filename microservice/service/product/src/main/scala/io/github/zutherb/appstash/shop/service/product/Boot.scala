package io.github.zutherb.appstash.shop.service.product

import java.util.Date

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import io.github.zutherb.appstash.shop.service.product.model.SystemConfiguration
import io.github.zutherb.appstash.shop.service.product.rest.ProductServiceActor

import spray.can.Http
import scala.collection.JavaConversions._

object Boot extends App {
  private val config = ConfigFactory.load()

  val hostUri = sys.env.getOrElse("MONGODB_PORT_27017_TCP_ADDR", config.getString("mongodb.host")) + ":" +
    sys.env.getOrElse("MONGODB_PORT_27017_TCP_PORT", config.getString("mongodb.port"))

  val systemConfig = new SystemConfiguration(
      config.getString("http.host"),
      config.getInt("http.port"),
      hostUri,
      config.getString("mongodb.db"),
      config.getString("mongodb.collection"),
      propertiesAsScalaMap(System.getProperties).toMap,
      sys.env,
      new Date().getTime)

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[ProductServiceActor], "product-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = systemConfig.httpHost, port = systemConfig.httpPort)
}