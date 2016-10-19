package io.github.zutherb.appstash.shop.ui.panel.product;

import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author zutherb
 */
public class RecommendationItemPanel extends ProductItemPanel {

    public RecommendationItemPanel(String id, Component feedback, IModel<ProductInfo> productInfoModel, IModel<List<String>> additionalTagsModel) {
        super(id, feedback, productInfoModel, additionalTagsModel);
        addTag(additionalTagsModel);
    }

    private void addTag(IModel<List<String>> additionalTagsModel) {
        additionalTagsModel.getObject().add("recommendation");
    }
}
