package io.github.zutherb.appstash.shop.service.cart.model;

import io.github.zutherb.appstash.common.util.SeoUtils;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
public class CartItemInfoTest {

    private CartItemInfo cartItemInfo;

    @Before
    public void setup() {
        cartItemInfo = new CartItemInfo(new ProductInfo(new ObjectId().toString(), "B2", "Salami", SeoUtils.urlFriendly("Salami"), "", ProductType.HANDY, 2.5, "category"));
    }

    @Test
    public void testGetTotalSum() {
        assertEquals(BigDecimal.valueOf(2.5), cartItemInfo.getTotalSum());
    }
}
