package io.github.zutherb.appstash.shop.service.cart.api;

public interface CartResolver extends Cart {
    String getActiveCartFulfillmentProviderName();
    void next();
}
