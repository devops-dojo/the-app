package io.github.zutherb.appstash.shop.ui.panel.cart;

import io.github.zutherb.appstash.common.util.Config;
import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.shop.ui.event.cart.CartChangeEvent;
import io.github.zutherb.appstash.shop.ui.event.cart.RemoveFromCartEvent;
import io.github.zutherb.appstash.shop.ui.model.PriceModel;
import io.github.zutherb.appstash.shop.ui.page.checkout.CheckoutPage;
import io.github.zutherb.appstash.shop.ui.panel.base.AbstractShopBasePanel;
import io.github.zutherb.appstash.shop.ui.panel.base.HighLightBehavior;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.List;

public class CartPanel extends AbstractShopBasePanel {

    @SpringBean(name = "cart")
    private Cart cart;

    private ListView<CartItemInfo> cartView;

    public CartPanel(String id) {
        super(id);
        add(discountHeader());
        add(discountTr());
        add(totalSum());
        add(checkoutLink());
        add(cartView());

        setOutputMarkupId(true);

        add(new HighLightBehavior());
    }

    private Component discountHeader(){
        WebMarkupContainer webMarkupContainer = new WebMarkupContainer ("discountHeader");
        //If there are no promotions hide the title
        if (Config.getProperty("GLOBAL_DISCOUNT")==null ||
            Double.parseDouble(Config.getProperty("GLOBAL_DISCOUNT"))==0){
            webMarkupContainer.setVisible(false);
        } else {
          webMarkupContainer.add(new Label("discountPercent", Config.getProperty("GLOBAL_DISCOUNT")));
        }
        return webMarkupContainer;
    }

    private Component discountTr(){
        WebMarkupContainer webMarkupContainer = new WebMarkupContainer ("discountTr");
        webMarkupContainer.add(discountSum());
        //If there are no promotions hide the TR
        if (Config.getProperty("GLOBAL_DISCOUNT")==null ||
            Double.parseDouble(Config.getProperty("GLOBAL_DISCOUNT"))==0){
            webMarkupContainer.setVisible(false);
        }

        return webMarkupContainer;
    }

    private Label discountSum() {
      return new Label("discount", new PriceModel(new PropertyModel<>(cart, "discountSum")));
    }

    private Label totalSum() {
        return new Label("price", new PriceModel(new PropertyModel<>(cart, "totalSum")));
    }

    private BookmarkablePageLink<Void> checkoutLink() {
        return new BookmarkablePageLink<>("checkout", CheckoutPage.class);
    }

    private Component cartView() {
        cartView = new ListView<CartItemInfo>("cart", cartListModel()) {
            @Override
            protected void populateItem(ListItem<CartItemInfo> item) {
                WebMarkupContainer cartItem = new WebMarkupContainer("item");
                cartItem.add(new Label("name", new PropertyModel<String>(item.getModel(), "product.name")));
                cartItem.add(new IndicatingAjaxLink<Void>("delete") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        IModel<CartItemInfo> model = item.getModel();
                        send(CartPanel.this, Broadcast.BREADTH, new RemoveFromCartEvent(model.getObject(), target));
                    }
                });
                cartItem.add(new Label("price", new PriceModel(new PropertyModel<>(item.getModel(), "totalSum"))));
                item.add(cartItem);
            }
        };
        cartView.setReuseItems(false);
        cartView.setOutputMarkupId(true);
        return cartView;
    }

    private IModel<List<CartItemInfo>> cartListModel() {
        return new LoadableDetachableModel<List<CartItemInfo>>() {
            @Override
            protected List<CartItemInfo> load() {
                try {
                    return cart.getAll();
                } catch (Exception e) {
                    getSession().error("Cart is not available yet, please try again later.");
                    return Collections.emptyList();
                }
            }
        };
    }

    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof CartChangeEvent) {
            cartView.getModel().detach();
            ((CartChangeEvent) event.getPayload()).getTarget().add(this);
        }
    }
}
