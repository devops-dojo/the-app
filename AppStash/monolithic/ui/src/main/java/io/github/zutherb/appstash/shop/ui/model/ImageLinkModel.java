package io.github.zutherb.appstash.shop.ui.model;

import com.google.common.collect.ImmutableMap;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.apache.wicket.util.string.interpolator.VariableInterpolator;

public class ImageLinkModel extends AbstractReadOnlyModel<String> {

    private static final String TEMPLATE = "${contextPath}/assets/img/${type}/${name}.0.jpg";
    private IModel<ProductInfo> productInfoModel;
    private Component parent;

    public ImageLinkModel(IModel<ProductInfo> productInfoModel, Component parent) {
        this.productInfoModel = productInfoModel;
        this.parent = parent;
    }

    @Override
    public String getObject() {
        VariableInterpolator interpolator = new MapVariableInterpolator(TEMPLATE, ImmutableMap.builder()
                .put("contextPath", parent.getRequestCycle().getRequest().getContextPath())
                .put("type", productInfoModel.getObject().getType().getUrlname())
                .put("name", productInfoModel.getObject().getUrlname())
                .build());
        return interpolator.toString();
    }

}
