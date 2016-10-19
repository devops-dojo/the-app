package io.github.zutherb.appstash.shop.service.product.model

import spray.json._

case class SystemConfiguration(val httpHost: String,
                         val httpPort: Int,
                         val databaseHost: String,
                         val databaseName: String,
                         val collectionName: String,
                         val systemVars: Map[String, String],
                         val envVars: Map[String, String],
                         val serverStartedAt : Long)

object SystemConfiguration extends DefaultJsonProtocol {

  implicit val systemConfigurationJsonFormat = jsonFormat8(SystemConfiguration.apply _)

}