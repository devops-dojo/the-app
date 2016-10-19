package io.github.zutherb.appstash.shop.service.product.impl

import akka.actor.{ActorSystem => AkkaActorSystem}
import io.github.zutherb.appstash.shop.service.product.Boot
import io.github.zutherb.appstash.shop.service.product.api.{MongoDBModule, ConfigurationModule}
import io.github.zutherb.appstash.shop.service.product.model.SystemConfiguration

import reactivemongo.api.MongoDriver

import scala.concurrent.ExecutionContext.Implicits.global

trait ShopDBModule extends MongoDBModule {
  self: ConfigurationModule  =>

  val system: AkkaActorSystem = Boot.system
  val config: SystemConfiguration = Configuration().get()

  override def MongoDB() = new MongoDB

  class MongoDB extends MongoDBLike {
    override def get() = {
      val driver = new MongoDriver(system)
      val connection = driver.connection(List(config.databaseHost))
      connection(config.databaseName)
    }
  }
}
