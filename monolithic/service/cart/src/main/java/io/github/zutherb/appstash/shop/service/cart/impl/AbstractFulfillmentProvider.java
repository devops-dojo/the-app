package io.github.zutherb.appstash.shop.service.cart.impl;

import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;

import java.math.BigDecimal;
import java.util.List;

public abstract class AbstractFulfillmentProvider {
    
    public BigDecimal getTotalSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItemInfo cartItemInfo : getAllItems()) {
            sum = sum.add(cartItemInfo.getTotalSum());
        }
        // double fraction = 0.25/100;        
        // return sum.subtract(sum.multiply(new BigDecimal(fraction)));
        
        return sum;
    }
    
    public BigDecimal getDiscountSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItemInfo cartItemInfo : getAllItems()) {
            sum = sum.add(cartItemInfo.getTotalSum());
        }
        
        double fraction = 0.25/100;
        return sum.multiply(new BigDecimal(fraction));
    }

    public abstract List<CartItemInfo> getAllItems();
}
