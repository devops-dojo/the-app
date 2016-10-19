package io.github.zutherb.appstash.shop.service.cart.impl;

import io.github.zutherb.appstash.shop.repository.cart.api.CartRepository;
import io.github.zutherb.appstash.shop.repository.cart.model.CartItem;
import io.github.zutherb.appstash.shop.service.cart.api.RedisMicroserviceCartFulfillmentProvider;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.strip;

/**
 * @author zutherb
 */
@Component(RedisMicroserviceCartFulfillmentProviderImpl.BEAN_NAME)
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class RedisMicroserviceCartFulfillmentProviderImpl extends AbstractFulfillmentProvider implements RedisMicroserviceCartFulfillmentProvider {
    public static final String BEAN_NAME = "redisMicroserviceCart";

    private Logger logger = LoggerFactory.getLogger(RedisMicroserviceCartFulfillmentProviderImpl.class);
    private final Object lock = new Object();

    private CartRepository cartRepository;
    private Mapper mapper;

    private String cartId;

    @Autowired
    public RedisMicroserviceCartFulfillmentProviderImpl(CartRepository cartRepository,
                                                        @Qualifier("dozerMapper") Mapper mapper) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
    }

    @Override
    public CartItemInfo addItem(ProductInfo productInfo) {
        synchronized (lock) {
            CartItemInfo cartItemInfo = createCartItemInfo(productInfo);
            CartItem map = mapToCartItem(cartItemInfo);
            if (StringUtils.isEmpty(cartId)) {
                cartId = strip(cartRepository.create(map), "\"");
            } else {
                cartRepository.add(cartId, map);
            }
            return cartItemInfo;
        }
    }

    private CartItemInfo createCartItemInfo(ProductInfo productInfo) {
        return new CartItemInfo(productInfo);
    }

    private CartItem mapToCartItem(CartItemInfo cartItemInfo) {
        return mapper.map(cartItemInfo, CartItem.class);
    }

    @Override
    public void removeItem(CartItemInfo item) {
        synchronized (lock) {
            cartRepository.removeFromCart(cartId, item.getUuid());
        }
    }

    @Override
    public List<CartItemInfo> getAll() {
        synchronized (lock) {
            return getAllItems();
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            if (StringUtils.isNotEmpty(cartId)) {
                cartRepository.clear(cartId);
                cartId = null;
                logger.info("Cart was cleared");
            }
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lock) {
            return getAllItems().isEmpty();
        }
    }

    @Override
    public List<CartItemInfo> getAllItems() {
        if (StringUtils.isNotEmpty(cartId)) {
            return cartRepository.getCartItems(cartId)
                    .stream()
                    .map(cardItem -> mapper.map(cardItem, CartItemInfo.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void setCartId(String cartId) {
        synchronized (lock) {
            this.cartId = cartId;
        }
    }
}
