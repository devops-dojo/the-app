package io.github.zutherb.appstash.shop.service.product

//import io.github.zutherb.appstash.shop.service.product.rest.ProductService
//import org.specs2.mutable.Specification
//import spray.http.StatusCodes._
//import spray.http._
//import spray.testkit.Specs2RouteTest

//class ProductServiceSpec extends Specification with Specs2RouteTest with ProductService {
//  def actorRefFactory = system
//
//  "MyService" should {
//
//    "return a greeting for GET requests to the root path" in {
//      Get("/products") ~> myRoute ~> check {
//        status === OK
//        responseAs[String] must contain("Margherita")
//      }
//    }
//
//    "leave GET requests to other paths unhandled" in {
//      Get("/kermit") ~> myRoute ~> check {
//        handled must beFalse
//      }
//    }
//
//    "return a MethodNotAllowed error for PUT requests to the root path" in {
//      Put("/products") ~> sealRoute(myRoute) ~> check {
//        status === MethodNotAllowed
//        responseAs[String] === "HTTP method not allowed, supported methods: GET"
//      }
//    }
//  }
//}
