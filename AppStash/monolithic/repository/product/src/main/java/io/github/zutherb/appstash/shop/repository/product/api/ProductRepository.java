package io.github.zutherb.appstash.shop.repository.product.api;


import io.github.zutherb.appstash.common.repository.AbstractRepository;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductQuery;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.common.repository.AbstractRepository;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductQuery;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;

import java.util.List;


public interface ProductRepository extends AbstractRepository<Product> {

    List<Product> findAll(ProductType type);

    Product findByArticleId( String articleId );

    Product findByUrlname( String urlname );

    Product findByQuery( ProductQuery productQuery);

    List<Product> findAllSortedByClassifier();
}
