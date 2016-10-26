package io.github.zutherb.appstash.shop.ui.application;

import io.github.zutherb.appstash.shop.service.cart.api.CartResolver;
import io.github.zutherb.appstash.shop.service.cart.api.RedisMicroserviceCartFulfillmentProvider;
import io.github.zutherb.appstash.shop.service.cart.impl.RedisMicroserviceCartFulfillmentProviderImpl;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CartRequestCycleListener extends AbstractRequestCycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartRequestCycleListener.class);
    private static final String CART_PARAMETER = "cart";

    @SpringBean(name = "redisMicroserviceCart")
    private RedisMicroserviceCartFulfillmentProvider cart;

    @SpringBean(name = "cart")
    private CartResolver cartResolver;

    public CartRequestCycleListener() {
        super();
        Injector.get().inject(this);
    }

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        StringValue cartParameterValue = cycle.getRequest().getRequestParameters().getParameterValue(CART_PARAMETER);
        if (!cartParameterValue.isEmpty()) {
            try {
                selectedRedisMicroserviceCart();
                cart.setCartId(cartParameterValue.toString());
                LOGGER.info(String.format("Session '%s' set cart '%s'", ShopSession.get().getId(), cartParameterValue.toString()));
            } catch (Exception e) {
                LOGGER.error("Cart processing failed:", e);
            }
        }
    }

    private void selectedRedisMicroserviceCart() {
        while (!RedisMicroserviceCartFulfillmentProviderImpl.BEAN_NAME.equals(cartResolver.getActiveCartFulfillmentProviderName())) {
            cartResolver.next();
        }
    }
}
