package io.github.zutherb.appstash.shop.service.product.model

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONObjectID}
import spray.json._

case class Product(var id: String,
                   articleId: String,
                   name: String,
                   urlname: String,
                   description: String,
                   productType: String,
                   price: Double)

object Product extends DefaultJsonProtocol {

  implicit val productJsonFormat = jsonFormat7(Product.apply _)

  implicit object PersonReader extends BSONDocumentReader[Product] {
    def read(doc: BSONDocument): Product = {
      val id = doc.getAs[BSONObjectID]("_id").get
      val articleId = doc.getAs[String]("articleId").get
      val name = doc.getAs[String]("name").get
      val urlname = doc.getAs[String]("urlname").get
      val description = doc.getAs[String]("description").get
      val productType = doc.getAs[String]("type").get
      val price = doc.getAs[Double]("price").get

      Product(id.stringify, articleId, name, urlname, description, productType, price)
    }
  }

}