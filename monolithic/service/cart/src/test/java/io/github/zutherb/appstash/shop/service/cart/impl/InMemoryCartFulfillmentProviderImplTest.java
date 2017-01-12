package io.github.zutherb.appstash.shop.service.cart.impl;

import io.github.zutherb.appstash.common.util.SeoUtils;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author zutherb
 */
public class InMemoryCartFulfillmentProviderImplTest {

    private CartFulfillmentProvider cart;

    @Before
    public void setUp() throws Exception {
        cart = new InMemoryCartFulfillmentProviderImpl();
        cart.addItem(new ProductInfo(new ObjectId().toString(), "A1", "Salami", SeoUtils.urlFriendly("Salami"), "", ProductType.HANDY, 10.0, "category"));
    }

    @Test
    public void testAddItem() throws Exception {
        assertFalse(cart.isEmpty());
    }

    @Test
    public void testRemoveItem() throws Exception {
        List<CartItemInfo> cartItemInfos = cart.getAll();
        assertEquals(1, cartItemInfos.size());
        cart.removeItem(cartItemInfos.get(0));
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testClearAll() throws Exception {
        cart.clear();
        assertTrue(cart.isEmpty());
    }
}
