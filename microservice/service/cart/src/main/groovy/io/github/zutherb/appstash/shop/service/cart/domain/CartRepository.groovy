package io.github.zutherb.appstash.shop.service.cart.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class CartRepository {
    private static final BASKET_UUID_PREFIX = "BASKET:"

    def RedisTemplate<String, CartItem> redisTemplate

    @Autowired
    def CartRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate
    }

    def Cart cart(String id) {
        def opsForList = redisTemplate.opsForList()
        def cartItems = opsForList.range(getKey(id), 0, opsForList.size(id) - 1)
        new Cart(id, cartItems)
    }

    def UUID create(CartItem cartItem) {
        def uuid = UUID.randomUUID()
        //don't use getKey here
        add(uuid.toString(), cartItem)
        uuid
    }

    def add(String id, CartItem cartItem) {
        def opsForList = redisTemplate.opsForList()
        opsForList.rightPush(getKey(id), cartItem)
    }

    def removeFromCart(String cartId, String itemId) {
        //don't use getKey here
        def cartItems = cart(cartId).cartItems
        cartItems.forEach({
            cardItem ->
                if (cardItem.uuid == itemId)
                    redisTemplate.opsForList().remove(getKey(cartId), 1, cardItem)
        });
    }

    def clear(String cartId) {
        redisTemplate.delete(getKey(cartId))
    }

    def getKey(String key) {
        BASKET_UUID_PREFIX + key
    }
}
