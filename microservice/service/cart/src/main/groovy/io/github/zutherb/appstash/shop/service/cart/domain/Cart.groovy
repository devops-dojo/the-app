package io.github.zutherb.appstash.shop.service.cart.domain

import org.springframework.hateoas.ResourceSupport

class Cart  extends ResourceSupport implements Serializable {
    def String uuid;
    def List<CartItem> cartItems;

    Cart(String uuid, List<CartItem> cartItems) {
        this.uuid = uuid
        this.cartItems = cartItems
    }
}
