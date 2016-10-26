package io.github.zutherb.appstash.shop.ui.service.checkout;

import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.shop.service.checkout.api.Checkout;
import io.github.zutherb.appstash.shop.service.checkout.impl.CheckoutImpl;
import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.shop.service.checkout.api.Checkout;
import io.github.zutherb.appstash.shop.service.checkout.impl.CheckoutImpl;
import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.bson.types.ObjectId;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public class CheckoutImplTest {

    @Mock
    Cart cart;
    private Checkout checkout;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(cart.getAll()).thenReturn(createCartItems());
        when(cart.getTotalSum()).thenReturn(BigDecimal.valueOf(2));
        checkout = new CheckoutImpl(cart, new DozerBeanMapper(
                Arrays.asList("io/github/zutherb/appstash/shop/service/checkout/dozer-mapping.xml")));
    }

    private List<CartItemInfo> createCartItems() {
        return Arrays.asList(new CartItemInfo( new ProductInfo(new ObjectId().toString(), "1", "test", "test", "null", ProductType.HANDY, 3.5,"test") ));
    }

    @Test
    public void testGetOrderItemInfos() throws Exception {
        List<OrderItemInfo> orderItemInfos = checkout.getOrderItemInfos();
        assertEquals(1, orderItemInfos.size());
    }

    @Test
    public void testGetTotalSum() throws Exception {
        int compareTo = BigDecimal.valueOf(2.0).compareTo(checkout.getTotalSum());
        assertEquals(compareTo, 0);
    }
}
