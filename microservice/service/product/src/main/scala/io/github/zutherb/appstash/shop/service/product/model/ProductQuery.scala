package io.github.zutherb.appstash.shop.service.product.model

import reactivemongo.bson.{BSONDocument, BSONDocumentWriter, BSONObjectID}

case class ProductQuery(id: Option[String],
                        productType: Option[String],
                        articleId: Option[String],
                        name: Option[String],
                        urlname: Option[String])

object ProductQuery {

  implicit object ProductQueryWriter extends BSONDocumentWriter[ProductQuery] {
    def write(query: ProductQuery): BSONDocument = {
      var document: BSONDocument = BSONDocument()
      if (!query.id.isEmpty && !query.id.get.isEmpty) {
        document = document ++ BSONDocument("_id" -> BSONObjectID(query.id.get))
      }
      if (!query.productType.isEmpty && !query.productType.get.isEmpty) {
        document = document ++ BSONDocument("type" -> query.productType.get)
      }
      if (!query.articleId.isEmpty && !query.articleId.get.isEmpty) {
        document = document ++ BSONDocument("articleId" -> query.articleId.get)
      }
      if (!query.name.isEmpty && !query.name.get.isEmpty) {
        document = document ++ BSONDocument("name" -> query.name.get)
      }
      if (!query.urlname.isEmpty && !query.urlname.get.isEmpty) {
        document = document ++ BSONDocument("urlname" -> query.urlname.get)
      }
      document
    }
  }

}
