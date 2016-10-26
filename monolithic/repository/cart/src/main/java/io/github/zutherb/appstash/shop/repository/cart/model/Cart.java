package io.github.zutherb.appstash.shop.repository.cart.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Cart implements Serializable {

    private String uuid;
    private List<CartItem> cartItems;

    private Cart() {
    }

    public String getUuid() {
        return uuid;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (!uuid.equals(cart.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
