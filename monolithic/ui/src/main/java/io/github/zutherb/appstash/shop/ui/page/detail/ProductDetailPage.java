package io.github.zutherb.appstash.shop.ui.page.detail;


import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.product.api.ProductService;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.service.recommendation.api.RecommendationService;
import io.github.zutherb.appstash.shop.ui.event.cart.AddToCartEvent;
import io.github.zutherb.appstash.shop.ui.model.ImageLinkModel;
import io.github.zutherb.appstash.shop.ui.model.PriceModel;
import io.github.zutherb.appstash.shop.ui.page.AbstractBasePage;
import io.github.zutherb.appstash.shop.ui.panel.cart.CartPanel;
import io.github.zutherb.appstash.shop.ui.panel.product.RecommendationItemListPanel;
import io.github.zutherb.appstash.shop.ui.tracking.TrackingService;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MountPath("productdetail/${urlname}")
public class ProductDetailPage extends AbstractBasePage {

    @SpringBean(name = "cart")
    private Cart cart;

    @SpringBean(name = "recommendationService")
    private RecommendationService recommendationService;

    @SpringBean(name = "productService")
    private ProductService productService;

    @SpringBean
    private TrackingService trackingService;

    private IModel<ProductInfo> productInfoModel;

    public ProductDetailPage(PageParameters pageParameters) {
        super(pageParameters);
        initialize(getProductInfoModelByUrlName(pageParameters));
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        trackingService.trackProductView(Arrays.asList(productInfoModel.getObject()), getAuthenticationService().getAuthenticatedUserInfo());
    }

    private LoadableDetachableModel<ProductInfo> getProductInfoModelByUrlName(final PageParameters pageParameters) {
        return new LoadableDetachableModel<ProductInfo>() {
            @Override
            protected ProductInfo load() {
                StringValue value = pageParameters.get("urlname");
                return productService.findByUrlname(value.toString());
            }
        };
    }

    public ProductDetailPage(IModel<ProductInfo> productInfoModel) {
        super(productInfoModel);
        initialize(productInfoModel);
    }

    private void initialize(IModel<ProductInfo> productInfoModel) {
        this.productInfoModel = productInfoModel;

        add(productNameLabel());
        add(productDescriptionLabel());
        add(productPriceLabel());
        add(addToCartLink());
        add(productImage());
        add(cartPanel());
        add(otherUsersAlsoViewedPanel());

        setOutputMarkupId(true);
    }

    private Label productNameLabel() {
        return new Label("productName", new PropertyModel(productInfoModel, "name"));
    }

    private Label productDescriptionLabel() {
        return new Label("productDescription", new PropertyModel(productInfoModel, "description"));
    }

    private Label productPriceLabel() {
        return new Label("productPrice", new PriceModel(new PropertyModel<>(productInfoModel, "price")));
    }

    private AbstractLink addToCartLink() {
        return new IndicatingAjaxLink<Void>("addToCart") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.add(feedback);
                send(getPage(), Broadcast.BREADTH, new AddToCartEvent(target, getPage(), productInfoModel.getObject(), getTags()));
            }
        };
    }

    private Component productImage() {
        WebMarkupContainer productImage = new WebMarkupContainer("productImage");
        productImage.add(new AttributeModifier("src", new ImageLinkModel(productInfoModel, this)));
        return productImage;
    }

    private CartPanel cartPanel() {
        return new CartPanel("cartPanel");
    }

    private RecommendationItemListPanel otherUsersAlsoViewedPanel() {
        return new RecommendationItemListPanel("otherUsersAlsoViewedProducts", feedback, "OTHER_UERS_ALSO_VIEWED", new Model<>("Other users also viewed"),
                new LoadableDetachableModel<List<ProductInfo>>() {
                    @Override
                    protected List<ProductInfo> load() {
                        return recommendationService.getViewedByOthersRecommendations(productInfoModel.getObject().getArticleId(), 4);
                    }
                });
    }

    public List<String> getTags() {
        return Arrays.asList("detail");
    }
}
