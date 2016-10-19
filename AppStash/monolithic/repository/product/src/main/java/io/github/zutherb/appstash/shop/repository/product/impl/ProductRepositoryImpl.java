package io.github.zutherb.appstash.shop.repository.product.impl;

import io.github.zutherb.appstash.common.repository.AbstractRepositoryImpl;
import io.github.zutherb.appstash.shop.repository.product.api.ProductRepository;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductQuery;
import io.github.zutherb.appstash.shop.repository.product.model.ProductQueryUtils;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("productRepository")
public class ProductRepositoryImpl extends AbstractRepositoryImpl<Product> implements ProductRepository {


    @Autowired
    public ProductRepositoryImpl(@Qualifier("mongoTemplate") MongoOperations mongoOperations) {
        super(mongoOperations, Product.class);
    }

    @Override
    public List<Product> findAll(ProductType type) {
        return mongoOperations.find(new Query(Criteria.where("type").is(type)), Product.class);
    }

    @Override
    public Product findByArticleId(String articleId) {
        return mongoOperations.findOne(new Query(Criteria.where("articleId").is(articleId)), Product.class);
    }

    @Override
    public Product findByUrlname(String urlname) {
        return mongoOperations.findOne(new Query(Criteria.where("urlname").is(urlname)), Product.class);
    }

    @Override
    public Product findByQuery(ProductQuery productQuery) {
        Query query = new Query();
        ProductQueryUtils.applyCriterias(query, productQuery);
        return mongoOperations.findOne(query, Product.class);
    }

    @Override
    public List<Product> findAllSortedByClassifier() {
        Query query = new Query();
        return mongoOperations.find(query, Product.class);
    }
}
