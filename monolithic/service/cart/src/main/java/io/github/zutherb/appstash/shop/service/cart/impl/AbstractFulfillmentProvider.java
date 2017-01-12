package io.github.zutherb.appstash.shop.service.cart.impl;

import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.common.util.Config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class AbstractFulfillmentProvider {
    
    public BigDecimal getTotalSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItemInfo cartItemInfo : getAllItems()) {
            sum = sum.add(cartItemInfo.getTotalSum());
        }
        
        double globalDiscount = Double.parseDouble(Config.getProperty("GLOBAL_DISCOUNT"));
        double factor = globalDiscount/100.0; 
        double result = sum.doubleValue() - (sum.doubleValue() * factor);
        return new BigDecimal(result);
    }
    
    public BigDecimal getDiscountSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItemInfo cartItemInfo : getAllItems()) {
            sum = sum.add(cartItemInfo.getTotalSum());
        }

        double globalDiscount = Double.parseDouble(Config.getProperty("GLOBAL_DISCOUNT"));
        double factor = globalDiscount/100.0;
        double result = sum.doubleValue() * factor;
        
        return new BigDecimal(result);
    }

    public abstract List<CartItemInfo> getAllItems();
}
