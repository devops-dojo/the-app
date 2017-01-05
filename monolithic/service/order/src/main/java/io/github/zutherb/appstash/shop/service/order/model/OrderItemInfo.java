package io.github.zutherb.appstash.shop.service.order.model;

import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zutherb
 */
public class OrderItemInfo implements Serializable {

    private ProductInfo product;
    private String uuid;

    private OrderItemInfo() {
    }

    public OrderItemInfo(ProductInfo product, String uuid) {
        this.product = product;
        this.uuid = uuid;
    }

    public ProductInfo getProduct() {
        return product;
    }
  
    public BigDecimal getTotalSum() {
        BigDecimal totalSum = BigDecimal.ZERO;
        totalSum = totalSum.add(product.getPrice());
        return totalSum;
    }

    public String getUuid() {
        return uuid;
    }
}
