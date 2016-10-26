package io.github.zutherb.appstash.shop.repository.cart.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author zutherb
 */
public class CartItem implements Serializable {

    private String uuid;
    private Product product;

    private CartItem() {
    }

    public CartItem(Product product) {
        this.product = product;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem that = (CartItem) o;

        if (!uuid.equals(that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
