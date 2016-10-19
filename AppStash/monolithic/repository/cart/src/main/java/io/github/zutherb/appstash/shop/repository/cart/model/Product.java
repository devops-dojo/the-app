package io.github.zutherb.appstash.shop.repository.cart.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String articleId;
    private String name;
    private String urlname;
    private String description;
    private String type;
    private String category;
    private double price;

    public Product() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
