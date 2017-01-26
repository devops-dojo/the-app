package io.github.zutherb.appstash.shop.repository.order.model;

import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
//import io.github.zutherb.appstash.common.util.Config;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import org.junit.Ignore;

import static org.junit.Assert.assertEquals;

/**
 * @author zuther
 */
@Ignore("not ready yet") public class OrderTest {
    Order order;

    @Before
    public void setUp() throws Exception {
        order = new Order();

        Product product = new Product("1", ProductType.HANDY, "product", 5);

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
        //double globalDiscount = Double.parseDouble(Config.getProperty("GLOBAL_DISCOUNT"));
        double globalDiscount = 0;
        double total = 10.0 - (10.0 * (globalDiscount/100.0));
        assertEquals(BigDecimal.valueOf(total).compareTo(order.getTotalSum()), 0);
    }

    @Test
    public void testGetDiscountSum() throws Exception {
        //double globalDiscount = Double.parseDouble(Config.getProperty("GLOBAL_DISCOUNT"));
        double globalDiscount = 0;
        double discount = 10.0 * (globalDiscount/100.0);
        //assertEquals(BigDecimal.valueOf(discount).compareTo(order.getDiscountSum()), 0);
    }

}
