package io.github.zutherb.appstash.shop.repository.product.model;

import java.io.Serializable;

/**
 * @author lleovac
 */
public class ProductQuery implements Serializable {
    private String id;
    private String articleId;
    private String name;
    private String urlname;
    private ProductType type;

    private ProductQuery() { /* NOOP */ }

    public static ProductQuery create() {
        return new ProductQuery();
    }

    public String getArticleId() {
        return articleId;
    }

    public ProductQuery withArticleId(String articleId) {
        this.articleId = articleId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductQuery withName(String name) {
        this.name = name;
        return this;
    }

    public String getUrlname() {
        return urlname;
    }

    public ProductQuery withUrlname(String urlname) {
        this.urlname = urlname;
        return this;
    }

    public ProductType getType() {
        return type;
    }

    public ProductQuery setType(ProductType type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public ProductQuery withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductQuery that = (ProductQuery) o;

        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != that.type) return false;
        if (urlname != null ? !urlname.equals(that.urlname) : that.urlname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (urlname != null ? urlname.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
