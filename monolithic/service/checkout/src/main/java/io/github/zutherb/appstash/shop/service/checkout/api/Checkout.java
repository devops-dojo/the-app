package io.github.zutherb.appstash.shop.service.checkout.api;

import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zutherb
 */
public interface Checkout {
    List<OrderItemInfo> getOrderItemInfos();
    BigDecimal getTotalSum();
    BigDecimal getDiscountSum();
}
