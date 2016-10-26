package io.github.zutherb.appstash.shop.repository.cart.impl;

import io.github.zutherb.appstash.shop.repository.cart.api.CartRepository;
import io.github.zutherb.appstash.shop.repository.cart.model.Cart;
import io.github.zutherb.appstash.shop.repository.cart.model.CartItem;
import io.github.zutherb.appstash.shop.repository.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Component
public class CartRepositoryImpl implements CartRepository {

    private static final String CREATE = "/create";
    private static final String GET = "/get/{id}";
    private static final String ADD = "/add?cartId={cartId}";
    private static final String REMOVE = "/removeItemFromCart?cartId={cartId}&itemId={itemId}";
    private static final String CLEAR = "/clear?cartId={cartId}";

    private final UriTemplate CREATE_TEMPLATE;
    private final UriTemplate GET_TEMPLATE;
    private final UriTemplate ADD_TEMPLATE;
    private final UriTemplate REMOVE_TEMPLATE;
    private final UriTemplate CLEAR_TEMPLATE;

    private RestTemplate restTemplate;

    @Autowired
    public CartRepositoryImpl(@Value("${redis.cart.microservice.url}") String baseUrl,
                              RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        CREATE_TEMPLATE = new UriTemplate(baseUrl + CREATE);
        GET_TEMPLATE = new UriTemplate(baseUrl + GET);
        ADD_TEMPLATE = new UriTemplate(baseUrl + ADD);
        REMOVE_TEMPLATE = new UriTemplate(baseUrl + REMOVE);
        CLEAR_TEMPLATE = new UriTemplate(baseUrl + CLEAR);
    }

    @Override
    public String create(CartItem cartItem) {
        RequestEntity<CartItem> requestEntity = new RequestEntity<>(cartItem, HttpMethod.PUT, CREATE_TEMPLATE.expand());
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public void add(String cartId, CartItem cartItem) {
        URI add_uri = ADD_TEMPLATE.expand(cartId);
        restTemplate.postForObject(add_uri, cartItem, String.class);
    }

    @Override
    public void removeFromCart(String cartId, String itemId) {
        URI remove_uri = REMOVE_TEMPLATE.expand(cartId, itemId);
        restTemplate.delete(remove_uri);
    }

    @Override
    public void clear(String cartId) {
        URI clear_uri = CLEAR_TEMPLATE.expand(cartId);
        restTemplate.delete(clear_uri);
    }

    @Override
    public List<CartItem> getCartItems(String cartId) {
        Assert.notNull(cartId, "CartId mustn't be null");
        URI get_uri = GET_TEMPLATE.expand(cartId);
        Cart cart = restTemplate.getForObject(get_uri, Cart.class);
        return cart.getCartItems();
    }
}
