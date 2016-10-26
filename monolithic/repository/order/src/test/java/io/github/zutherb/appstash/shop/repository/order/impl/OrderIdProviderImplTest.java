package io.github.zutherb.appstash.shop.repository.order.impl;

import io.github.zutherb.appstash.shop.repository.order.api.OrderIdProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:io/github/zutherb/appstash/shop/repository/order/spring-context.xml")
public class OrderIdProviderImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private OrderIdProvider orderIdProvider;

    @Test
    public void testNextVal() throws Exception {
        Long orderId = orderIdProvider.nextVal();
        Long nextOrderId = orderIdProvider.nextVal();
        Long expected = orderId + 1;
        assertEquals(expected , nextOrderId);
    }
}
