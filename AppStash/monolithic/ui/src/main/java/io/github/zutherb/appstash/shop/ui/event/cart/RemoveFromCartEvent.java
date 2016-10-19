package io.github.zutherb.appstash.shop.ui.event.cart;


import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RemoveFromCartEvent implements CartChangeEvent {

    private final AjaxRequestTarget target;

    @SpringBean
    private Cart cart;

    public RemoveFromCartEvent(CartItemInfo cartItemInfo, AjaxRequestTarget target) {
        this.target = target;
        Injector.get().inject(this);
        cart.removeItem(cartItemInfo);
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
