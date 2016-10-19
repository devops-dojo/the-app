package io.github.zutherb.appstash.shop.service.product.api

import akka.actor.{ActorSystem => AkkaActorSystem}

import reactivemongo.api.{DB, MongoConnection, MongoDriver}

import scala.concurrent.ExecutionContext.Implicits.global

trait MongoDBModule {

  type MongoDB <: MongoDBLike

  def MongoDB(): MongoDB

  trait MongoDBLike {
    def get(): DB
  }

}
