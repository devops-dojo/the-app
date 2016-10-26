package io.github.zutherb.appstash.shop.repository.order.model;

import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author zuther
 */
public class OrderTest {
    Order order;

    @Before
    public void setUp() throws Exception {
        order = new Order();

        Product product = new Product("1", ProductType.HANDY, "product", 4);

        OrderItem orderItem1 = new OrderItem(product);
        OrderItem orderItem2 = new OrderItem(product);

        order.getOrderItems().add(orderItem1);
        order.getOrderItems().add(orderItem2);
    }

    @Test
    public void testGetProductCount() throws Exception {
        assertEquals(BigDecimal.valueOf(2).compareTo(order.getItemCount()), 0);
    }

    @Test
    public void testGetTotalSum() throws Exception {
        assertEquals(BigDecimal.valueOf(8).compareTo(order.getTotalSum()), 0);
    }
}
