package io.github.zutherb.appstash.shop.ui.panel.product;

import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author zutherb
 */
public class RecommendationItemListPanel extends ProductItemListPanel {

    private final Component feedback;

    public RecommendationItemListPanel(String id, Component feedback, String recommenderType, IModel<?> containerTopic, IModel<List<ProductInfo>> productListModel) {
        super(id, feedback, recommenderType, containerTopic, productListModel);
        this.feedback = feedback;
    }

    @Override
    protected Component newProductItemPanel(String id, String parentTag, IModel<ProductInfo> model) {
        return new RecommendationItemPanel(id, feedback, model, createTags(parentTag));
    }
}
