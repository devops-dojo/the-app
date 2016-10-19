package io.github.zutherb.appstash.shop.service.cart.impl;

import io.github.zutherb.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zutherb
 */
@Component("inMemoryCart")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class InMemoryCartFulfillmentProviderImpl extends AbstractFulfillmentProvider implements CartFulfillmentProvider {

    private Logger logger = LoggerFactory.getLogger(InMemoryCartFulfillmentProviderImpl.class);

    private List<CartItemInfo> items;

    public InMemoryCartFulfillmentProviderImpl() {
        items = new ArrayList<>();
    }

    @Override
    public CartItemInfo addItem(ProductInfo productInfo) {
        CartItemInfo cartItemInfo = new CartItemInfo(productInfo);
        getAllItems().add(cartItemInfo);
        return cartItemInfo;
    }

    @Override
    public void removeItem(CartItemInfo item) {
        getAllItems().remove(item);
    }

    @Override
    public List<CartItemInfo> getAll() {
        return getAllItems();
    }

    @Override
    public void clear() {
        getAllItems().clear();
        logger.info("Cart was cleared");
    }

    @Override
    public boolean isEmpty() {
        return getAllItems().isEmpty();
    }

    @Override
    public List<CartItemInfo> getAllItems() {
        return items;
    }
}
