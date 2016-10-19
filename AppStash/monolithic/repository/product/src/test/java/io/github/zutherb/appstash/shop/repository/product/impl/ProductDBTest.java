package io.github.zutherb.appstash.shop.repository.product.impl;

import io.github.zutherb.appstash.shop.repository.product.api.ProductRepository;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.repository.product.api.ProductRepository;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertEquals;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/27/13
 * Time: 5:03 PM
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:io/github/zutherb/appstash/shop/repository/product/spring-context.xml")
public class ProductDBTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void saveAndRetrieveOrderTest() {
        mongoOperations.dropCollection(Product.class);
        Product sampleProduct = createSampleProduct();
        productRepository.save(sampleProduct);

        assertEquals(1, productRepository.countAll());
        assertEquals(sampleProduct.getArticleId(), productRepository.findAll().get(0).getArticleId());
    }


    private static Product createSampleProduct() {
        return new Product("4242", ProductType.HANDY, "Nexus S", 5.95);
    }
}
