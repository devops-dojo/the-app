package io.github.zutherb.appstash.shop.ui.page.checkout;

import io.github.zutherb.appstash.shop.service.authentication.api.FakeAuthenticationService;
import io.github.zutherb.appstash.shop.service.checkout.api.Checkout;
import io.github.zutherb.appstash.shop.service.order.api.OrderService;
import io.github.zutherb.appstash.shop.service.order.model.DeliveryAddressInfo;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.service.recommendation.api.RecommendationService;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.shop.ui.event.cart.CartChangeEvent;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationItem;
import io.github.zutherb.appstash.shop.ui.page.AbstractBasePage;
import io.github.zutherb.appstash.shop.ui.page.home.HomePage;
import io.github.zutherb.appstash.shop.ui.panel.DeliveryAdressInfoPanel;
import io.github.zutherb.appstash.shop.ui.panel.OrderItemListPanel;
import io.github.zutherb.appstash.shop.ui.panel.product.RecommendationItemListPanel;
import io.github.zutherb.appstash.shop.ui.tracking.TrackingService;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.Collections;
import java.util.List;


/**
 * @author zuther
 */
@SuppressWarnings("UnusedDeclaration")
@MountPath("checkout")
@NavigationItem(name = "Proceed to Checkout", sortOrder = 3, visible = "!@cart.isEmpty()")
public class CheckoutPage extends AbstractBasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutPage.class);

    private static final long serialVersionUID = -6793984194989062010L;

    @SpringBean(name = "checkout")
    private Checkout checkout;

    @SpringBean(name = "orderService")
    private OrderService orderService;

    @SpringBean(name = "recommendationService")
    private RecommendationService recommendationService;

    @SpringBean(name = "fakeAuthenticationService")
    private FakeAuthenticationService fakeAuthenticationService;

    @SpringBean
    private TrackingService trackingService;

    IModel<UserInfo> userInfoModel;
    IModel<OrderInfo> orderInfoModel;

    public CheckoutPage() {
        super();
        orderInfoModel = orderInfoModel();
        validateCheckoutPage();
        initCheckoutPage();
    }


    CheckoutPage(IModel<OrderInfo> orderInfoModel) {
        super();
        this.orderInfoModel = orderInfoModel;
        validateCheckoutPage();
        initCheckoutPage();
    }

    private void initCheckoutPage() {
        userInfoModel = userInfoModel();
        if (!getAuthenticationService().isAuthorized()) {
            fakeAuthenticationService.authenticate();
            trackingService.trackLogin(getAuthenticationService().getAuthenticatedUserInfo());
        }
        add(orderForm());
        add(prepareFrequentlyBoughtWithPanel(checkout.getOrderItemInfos()));

    }

    protected void validateCheckoutPage() {
        try {
            if (checkout.getOrderItemInfos().isEmpty()) {
                getSession().error(getString("checkout.validation.failed"));
                LOGGER.debug("Basket is empty and CheckoutPage could not be created");
                throw new RestartResponseException(Application.get().getHomePage());
            }
        } catch (Exception e) {
            getSession().error("Cart is not available yet, please try again later.");
            LOGGER.error("A backend error seems to be occurred: ", e);
            throw new RestartResponseException(Application.get().getHomePage());
        }
    }

    private Component submitOrderLink() {
        return new SubmitLink("submitOrder") {
            private static final long serialVersionUID = 5203227218130238529L;

            @Override
            protected void onBeforeRender() {
                setVisible(!isReadOnly() && getAuthenticationService().isAuthorized());
                super.onBeforeRender();
            }

            @Override
            public void onSubmit() {
                OrderInfo submittedOrder = orderService.submitOrder(orderInfoModel.getObject(), getSession().getId());
                trackingService.trackPurchase(submittedOrder);

                getSession().info(CheckoutPage.this.getString("order.submitted"));
                setResponsePage(new OrderConfirmationPage(Model.of(submittedOrder)));
            }
        };
    }

    private IModel<UserInfo> userInfoModel() {
        return new LoadableDetachableModel<UserInfo>() {
            private static final long serialVersionUID = 2232385286290869095L;

            @Override
            protected UserInfo load() {
                return getAuthenticationService().getAuthenticatedUserInfo();
            }
        };
    }

    private IModel<OrderInfo> orderInfoModel() {
        return new LoadableDetachableModel<OrderInfo>() {
            private static final long serialVersionUID = -8140423224127500419L;

            @Override
            protected OrderInfo load() {
                if (getAuthenticationService().isAuthorized()) {
                    UserInfo authenticatedUser = userInfoModel.getObject();
                    return new OrderInfo(authenticatedUser, new DeliveryAddressInfo(authenticatedUser),
                            checkout.getOrderItemInfos(), getSession().getId());
                }
                return new OrderInfo(new UserInfo(), new DeliveryAddressInfo(), checkout.getOrderItemInfos(), "");
            }
        };
    }

    private Component orderForm() {
        Form<Void> orderForm = new Form<Void>("orderForm") {

            @Override
            public void onEvent(IEvent<?> event) {
                if (event.getPayload() instanceof CartChangeEvent) {
                    ((CartChangeEvent) event.getPayload()).getTarget().add(this);
                }
            }
        };
        orderForm.add(new DeliveryAdressInfoPanel("delieferyInfomation", orderInfoModel) {
            private static final long serialVersionUID = 8837712628875618582L;

            @Override
            public boolean isReadOnly() {
                return CheckoutPage.this.isReadOnly();
            }
        });
        orderForm.add(new OrderItemListPanel("orderItems", orderInfoModel));
        orderForm.add(submitOrderLink());
        orderForm.add(backToShopLink());
        return orderForm.setOutputMarkupId(true);
    }

    private Component backToShopLink() {
        return new BookmarkablePageLink<Void>("backToShopLink", HomePage.class) {
            @Override
            protected void onBeforeRender() {
                super.onBeforeRender();
                setVisible(isReadOnly());
            }
        };
    }

    protected Component prepareFrequentlyBoughtWithPanel(final List<OrderItemInfo> orderItemInfos) {
        IModel<List<ProductInfo>> productListModel = !showFrequentlyBoughtWithPanel()
                ? emptyListModel() : productListModel(orderItemInfos);
        return new RecommendationItemListPanel("frequentlyBoughtWithProducts", feedback, "FREQUENTLY_BOUGHT",
                new Model<>("Frequently bought with"), productListModel);
    }

    private IModel<List<ProductInfo>> emptyListModel() {
        return new ListModel<>(Collections.<ProductInfo>emptyList());
    }

    private LoadableDetachableModel<List<ProductInfo>> productListModel(final List<OrderItemInfo> orderItemInfos) {
        return new LoadableDetachableModel<List<ProductInfo>>() {
            @Override
            protected List<ProductInfo> load() {
                return recommendationService.getFrequentlyBoughtWithProductsRecommendations(3);
            }
        };
    }

    protected boolean isReadOnly() {
        return false;
    }

    protected boolean showFrequentlyBoughtWithPanel() {
        return true;
    }
}


