package io.github.zutherb.appstash.shop.ui.event.cart;


import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.ui.application.ShopSession;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class AddToCartEvent implements CartChangeEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartEvent.class);
    public static final String ADD_TO_CART_ACTION_COULD_NOT_BE_PROCESSED_MESSAGE = "Add to cart action could not be processed";

    private final AjaxRequestTarget target;

    @SpringBean
    private Cart cart;

    public AddToCartEvent(AjaxRequestTarget target, Component component, ProductInfo product, List<String> tags) {
        Injector.get().inject(this);

        this.target = target;
        addProductToBasket(product);
    }

    private void addProductToBasket(ProductInfo product) {
        try {
            cart.addItem(product);
        } catch (Exception e) {
            ShopSession.get().error(ADD_TO_CART_ACTION_COULD_NOT_BE_PROCESSED_MESSAGE);
            LOGGER.error("Add to cart action could not be processed", e);
        }
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
