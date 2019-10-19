package io.github.zutherb.appstash.shop.ui.panel;

import io.github.zutherb.appstash.common.util.Config;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;
import io.github.zutherb.appstash.shop.ui.model.PriceModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

/**
 * @author zutherb
 */
public class OrderItemListPanel extends Panel {

    public OrderItemListPanel(String id, IModel<OrderInfo> orderInfoModel) {
        super(id, orderInfoModel);
        add(discountHeader());
        add(orderItemList());
        add(discountTr());
        add(totalSum());
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

    private Component discountSum() {
        return new Label("discountSum", new PriceModel(new PropertyModel<>(getDefaultModel(), "discountSum")));

    }

    private Component totalSum() {
        return new Label("totalSum", new PriceModel(new PropertyModel<>(getDefaultModel(), "totalSum")));
    }

    private Component orderItemList() {
        return new ListView<OrderItemInfo>("orderItems", new PropertyModel<List<OrderItemInfo>>(getDefaultModel(), "orderItems")) {

            private int orderItemCounter = 1;

            @Override
            protected void populateItem(ListItem<OrderItemInfo> orderItem) {
                orderItem.add(new Label("orderItemCounter", Model.of(orderItemCounter++)));
                orderItem.add(new Label("product", new PropertyModel<String>(orderItem.getModel(), "product.name")));
                orderItem.add(new Label("description", new PropertyModel<String>(orderItem.getModel(), "product.description")));
                orderItem.add(new Label("totalSum", new PriceModel(new PropertyModel<>(orderItem.getModel(), "totalSum"))));
            }

            @Override
            protected void onDetach() {
                orderItemCounter = 1;
                super.onDetach();
            }
        };
    }
}
