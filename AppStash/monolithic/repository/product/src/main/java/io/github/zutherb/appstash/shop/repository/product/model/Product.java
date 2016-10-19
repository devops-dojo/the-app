package io.github.zutherb.appstash.shop.repository.product.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author zutherb
 */
@Document(collection = Product.COLLECTION_NAME)
public class Product implements Serializable {
    public static final String COLLECTION_NAME = "product";

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String articleId;
    private String name;
    @Indexed(unique = true)
    private String urlname;
    private String description;
    @Indexed
    private ProductType type;
    private String category;
    private double price;

    public Product(String articleId, ProductType type, String name, double price) {
        this.articleId = articleId;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public Product() {
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (articleId != null ? !articleId.equals(product.articleId) : product.articleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return articleId != null ? articleId.hashCode() : 0;
    }
}
