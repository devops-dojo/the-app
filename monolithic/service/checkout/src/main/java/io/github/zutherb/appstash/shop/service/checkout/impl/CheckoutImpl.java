package io.github.zutherb.appstash.shop.service.checkout.impl;

import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.checkout.api.Checkout;
import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;
import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.checkout.api.Checkout;
import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zutherb
 */
@Component("checkout")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
public class CheckoutImpl implements Checkout {

    private Cart cart;
    private Mapper mapper;

    @Autowired
    public CheckoutImpl(@Qualifier("cart") Cart cart,
                        @Qualifier("dozerMapper") Mapper mapper) {
        this.cart = cart;
        this.mapper = mapper;
    }

    @Override
    public List<OrderItemInfo> getOrderItemInfos() {
        return cart.getAll().stream()
                .map(cartItemInfo -> mapper.map(cartItemInfo, OrderItemInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalSum() {
        return cart.getTotalSum();
    }
    
    @Override
    public BigDecimal getDiscountSum() {
        return cart.getDiscountSum();
    }
    
}
