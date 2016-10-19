package io.github.zutherb.appstash.shop.ui.panel.product;

import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.ui.event.cart.AddToCartEvent;
import io.github.zutherb.appstash.shop.ui.model.ImageLinkModel;
import io.github.zutherb.appstash.shop.ui.model.PriceModel;
import io.github.zutherb.appstash.shop.ui.page.detail.ProductDetailPage;
import io.github.zutherb.appstash.shop.ui.panel.base.AbstractShopBasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Collections;
import java.util.List;

/**
 * @author zutherb
 */
public class ProductItemPanel extends AbstractShopBasePanel {

    private Component feedback;

    private IModel<ProductInfo> productInfoModel;
    private IModel<List<String>> additionalTagsModel;
    private IModel<String> productUrlModel;

    public ProductItemPanel(String productItem, Component feedback, IModel<ProductInfo> model) {
        this(productItem, feedback, model, new ListModel<>(Collections.<String>emptyList()));
    }

    public ProductItemPanel(String id, Component feedback, IModel<ProductInfo> productInfoModel, IModel<List<String>> additionalTagsModel) {
        super(id, productInfoModel);
        this.productInfoModel = productInfoModel;
        this.additionalTagsModel = additionalTagsModel;
        this.feedback = feedback;

        add(productNameLabel());
        add(productPriceLabel());
        add(productDetailImageLink());
        add(addToCartLink());

        setOutputMarkupId(true);
    }

    @Override
    protected void onAfterRender() {
        super.onAfterRender();
        // this is required for that the correct URL is used even if a recommendation changes while viewing the page
        productUrlModel = Model.of(productInfoModel.getObject().getUrlname());
    }


    private Label productPriceLabel() {
        return new Label("price", new PriceModel(new PropertyModel<Number>(productInfoModel, "price")));
    }

    private Label productNameLabel() {
        return new Label("name", new PropertyModel<String>(productInfoModel, "name"));
    }

    private Component productDetailImageLink() {
        Link<Void> detailPageLink = new Link<Void>("productDetailLink") {
            @Override
            public void onClick() {
                PageParameters pageParameters = new PageParameters();
                pageParameters.set("urlname", productUrlModel.getObject());
                setResponsePage(new ProductDetailPage(pageParameters));
            }
        };
        WebMarkupContainer image = new WebMarkupContainer("image");
        image.add(new AttributeModifier("src", new ImageLinkModel(productInfoModel, this)));
        image.add(new AttributeModifier("title", new PropertyModel<String>(productInfoModel, "description")));
        image.add(new AttributeModifier("alt", new PropertyModel<String>(productInfoModel, "name")));
        image.setOutputMarkupId(true);

        detailPageLink.add(image);
        return detailPageLink;
    }

    private IndicatingAjaxLink<Void> addToCartLink() {
        IndicatingAjaxLink<Void> result = new IndicatingAjaxLink<Void>("addToCartIcon") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                //send wicket event for add to cart
                target.add(feedback);
                send(getPage(), Broadcast.BREADTH, new AddToCartEvent(target, getPage(), productInfoModel.getObject(), getTags()));
            }
        };
        result.setVisible(showAddToCartLink());
        return result;
    }

    protected List<String> getTags() {
        return additionalTagsModel.getObject();
    }

    protected boolean showAddToCartLink() {
        return true;
    }
}
