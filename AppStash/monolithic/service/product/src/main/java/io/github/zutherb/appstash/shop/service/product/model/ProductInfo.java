package io.github.zutherb.appstash.shop.service.product.model;


import io.github.zutherb.appstash.shop.repository.product.model.ProductType;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductInfo implements Serializable, Comparable<ProductInfo> {

    private String id;
    private String articleId;
    private String name;
    private String urlname;
    private String category;
    private String description;
    private ProductType type;
    private BigDecimal price;

    private ProductInfo() {
    }

    public ProductInfo(String id, String articleId, String name, String urlname, String description, ProductType type,
                       double price, String category) {
        this.id = id;
        this.articleId = articleId;
        this.name = name;
        this.urlname = urlname;
        this.description = description;
        this.type = type;
        this.price = BigDecimal.valueOf(price);
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }

    public String getUrlname() {
        return urlname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInfo productInfo = (ProductInfo) o;

        if (articleId != null ? !articleId.equals(productInfo.articleId) : productInfo.articleId != null) return false;

        return true;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return articleId != null ? articleId.hashCode() : 0;
    }

    @Override
    public int compareTo(ProductInfo productInfo) {
        return this.getName().compareTo(productInfo.getName());
    }
}