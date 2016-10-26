package io.github.zutherb.appstash.shop.service.cart.domain

class Product implements Serializable {
    def String id;
    def String articleId;
    def String name;
    def String urlname;
    def String category;
    def String description;
    def String type;
    def BigDecimal price;
}
