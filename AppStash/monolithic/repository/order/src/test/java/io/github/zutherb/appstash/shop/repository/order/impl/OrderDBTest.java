package io.github.zutherb.appstash.shop.repository.order.impl;

import io.github.zutherb.appstash.shop.repository.order.api.OrderRepository;
import io.github.zutherb.appstash.shop.repository.order.model.DeliveryAddress;
import io.github.zutherb.appstash.shop.repository.order.model.Order;
import io.github.zutherb.appstash.shop.repository.order.model.OrderItem;
import io.github.zutherb.appstash.shop.repository.product.api.ProductRepository;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.Address;
import io.github.zutherb.appstash.shop.repository.user.model.Role;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.shop.repository.order.api.OrderRepository;
import io.github.zutherb.appstash.shop.repository.order.model.DeliveryAddress;
import io.github.zutherb.appstash.shop.repository.order.model.Order;
import io.github.zutherb.appstash.shop.repository.order.model.OrderItem;
import io.github.zutherb.appstash.shop.repository.product.api.ProductRepository;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.Address;
import io.github.zutherb.appstash.shop.repository.user.model.Role;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/27/13
 * Time: 4:58 PM
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:io/github/zutherb/appstash/shop/repository/order/spring-context.xml")
public class OrderDBTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    private static User sampleUser;
    private static Product sampleProduct;

    @Autowired
    private MongoOperations mongoOperations;

    @Before
    public void setUp() {
        mongoOperations.dropCollection(User.class);
        mongoOperations.dropCollection(Product.class);

        userRepository.save(createSampleUser());
        productRepository.save(createSampleProduct());

        sampleUser = userRepository.findAll().get(0);
        sampleProduct = productRepository.findAll().get(0);
    }

    @Test
    public void saveAndRetrieveOrderTest() {
        mongoOperations.dropCollection(Order.class);

        Order sampleOrder = createSampleOrder();
        orderRepository.save(sampleOrder);

        Assert.assertEquals(1, orderRepository.countAll());
        Assert.assertEquals(sampleOrder.getOrderId(), orderRepository.findAll().get(0).getOrderId());
    }

    private static Order createSampleOrder() {
        return new Order(4242L,
                sampleUser.getId(),
                Collections.singletonList(new OrderItem(sampleProduct)),
                new DeliveryAddress(sampleUser.getAddress()),
                "the-session-id");
    }

    private static User createSampleUser() {
        return new User("johnsmith", "John", "Smith", "securepw", new Address("", "", "", ""), Collections.<Role>emptySet());
    }

    private static Product createSampleProduct() {
        return new Product("4242", ProductType.HANDY, "Nexus S", 5.95);
    }
}
