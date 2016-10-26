package io.github.zutherb.appstash.shop.service.cart.api;

public interface RedisMicroserviceCartFulfillmentProvider extends CartFulfillmentProvider {
    void setCartId(String cartId);
}
