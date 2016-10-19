package io.github.zutherb.appstash.shop.service.product.api

import io.github.zutherb.appstash.shop.service.product.model.Product
import io.github.zutherb.appstash.shop.service.product.model.ProductQuery

import scala.concurrent.Future

trait ProductRepositoryModule {

  type ProductRepository <: ProductRepositoryLike

  def ProductRepository(): ProductRepository

  trait ProductRepositoryLike {
    def findAll(): Future[List[Product]]

    def findByQuery(query: ProductQuery): Future[List[Product]]
  }

}
