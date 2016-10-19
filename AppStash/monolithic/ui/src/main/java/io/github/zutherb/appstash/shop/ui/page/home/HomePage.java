package io.github.zutherb.appstash.shop.ui.page.home;

import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.service.recommendation.api.RecommendationService;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationItem;
import io.github.zutherb.appstash.shop.ui.page.AbstractBasePage;
import io.github.zutherb.appstash.shop.ui.panel.cart.CartPanel;
import io.github.zutherb.appstash.shop.ui.panel.product.RecommendationItemListPanel;
import org.apache.wicket.Component;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

@NavigationItem(name = "Home", sortOrder = 1)
public class HomePage extends AbstractBasePage {

    @SpringBean(name = "recommendationService")
    private RecommendationService recommendationService;
    @SpringBean(name = "authenticationService")
    private AuthenticationService authenticationService;


    public HomePage() {
        super();

        add(topSellerPanel());
        add(youMayAlsoLikeProductsPanel());
        add(cartPanel());

        setOutputMarkupId(true);
    }

    private Component topSellerPanel() {
        boolean userAuthorized = isUserAuthorized();
        String ressourceKey = userAuthorized ? "your.favorite.products.topic" : "category.top.seller.topic";
        String recommenderType = userAuthorized ? "FAVORITE_PRODUCTS" : "STARTPAGE_TOPSELLER";

        return new RecommendationItemListPanel("topSellerProductsContainer", feedback,recommenderType, new ResourceModel(ressourceKey),
                new LoadableDetachableModel<List<ProductInfo>>() {
                    @Override
                    protected List<ProductInfo> load() {
                        return recommendationService.getTopsellerRecommendations(4);
                    }
                }) {
        };
    }

    private Component youMayAlsoLikeProductsPanel() {
        return new RecommendationItemListPanel("youMayAlsoLikeProductsContainer", feedback, "MAY_ALSO_LIKE", new ResourceModel("you.may.also.like.topic"),
                new LoadableDetachableModel<List<ProductInfo>>() {
                    @Override
                    protected List<ProductInfo> load() {
                        return recommendationService.getCollaborativeFilteringRecommendations(4);
                    }
                }) {
        };
    }

    private Component cartPanel() {
        return new CartPanel("cartPanel");
    }

    private boolean isUserAuthorized() {
        return authenticationService.isAuthorized() && authenticationService.getAuthenticatedUserInfo() != null;
    }
}
