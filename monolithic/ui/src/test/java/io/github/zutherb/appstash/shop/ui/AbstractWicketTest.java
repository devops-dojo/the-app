package io.github.zutherb.appstash.shop.ui;

import io.github.zutherb.appstash.shop.repository.product.api.ProductRepository;
import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import io.github.zutherb.appstash.shop.service.authentication.api.FakeAuthenticationService;
import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.checkout.api.Checkout;
import io.github.zutherb.appstash.shop.service.product.api.ProductService;
import io.github.zutherb.appstash.shop.service.recommendation.api.RecommendationService;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.ui.application.TestShopApplication;
import io.github.zutherb.appstash.shop.ui.mbean.DesignSelector;
import io.github.zutherb.appstash.shop.ui.mbean.FeatureTooglesBean;
import io.github.zutherb.appstash.shop.ui.navigation.Navigation;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationEntry;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationGroup;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationProvider;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public abstract class AbstractWicketTest {

    protected WicketTester wicketTester;

    @Mock
    protected AuthenticationService authenticationService;
    @Mock
    protected Cart cart;
    @Mock
    protected Checkout checkout;
    @Mock
    protected ProductRepository productRepository;
    @Mock
    protected ProductService productService;
    @Mock
    protected RecommendationService recommendationService;
    @Mock
    protected RestTemplate restTemplate;
    @Mock
    protected FakeAuthenticationService fakeAuthenticationService;
    @Mock
    protected NavigationProvider navigationProvider;
    @Mock
    protected UserService userService;
    @Mock
    protected FeatureTooglesBean featureTooglesBean;
    @Mock
    protected DesignSelector designSelectorBean;

    @Before
    public void initializeWicketTester() throws Exception {
        initMocks(this);
        ApplicationContextMock appctx = initializeApplicationContext();
        TestShopApplication application = new TestShopApplication(appctx);
        wicketTester = new WicketTester(application);
    }

    private ApplicationContextMock initializeApplicationContext() {
        Navigation navigationMock = createNavigationMock();
        when(navigationProvider.getNavigation()).thenReturn(navigationMock);

        ApplicationContextMock appctx = new ApplicationContextMock();
        appctx.putBean("authenticationService", authenticationService);
        appctx.putBean("cart", cart);
        appctx.putBean("checkout", checkout);
        appctx.putBean("productRepository", productRepository);
        appctx.putBean("productService", productService);
        appctx.putBean("frequentlyBoughtWithProductsRecommendationService", recommendationService);
        appctx.putBean("restTemplate", restTemplate);
        appctx.putBean("fakeAuthenticationService", fakeAuthenticationService);
        appctx.putBean("navigationProvider", navigationProvider);
        appctx.putBean("userService", userService);
        appctx.putBean("featureTooglesBean", featureTooglesBean);
        appctx.putBean("designSelector", designSelectorBean);
        appctx.putBean("dozerMapper", createDozerMapper());

        return appctx;
    }

    private Mapper createDozerMapper() {
        return new DozerBeanMapper(Arrays.asList("/io/github/zutherb/appstash/shop/service/user/dozer-mapping.xml",
                "/io/github/zutherb/appstash/shop/service/checkout/dozer-mapping.xml",
                "/io/github/zutherb/appstash/shop/service/order/dozer-mapping.xml",
                "/io/github/zutherb/appstash/shop/service/product/dozer-mapping.xml",
                "/io/github/zutherb/appstash/shop/service/rule/dozer-mapping.xml",
                "/io/github/zutherb/appstash/shop/ui/dozer-mapping.xml"));
    }

    private Navigation createNavigationMock() {
        Navigation navigation = mock(Navigation.class);
        NavigationGroup mainGroup = mock(NavigationGroup.class);
        when(mainGroup.getNavigationEntries()).thenReturn(Collections.<NavigationEntry>emptyList());
        when(navigation.getMainNavigationGroup()).thenReturn(mainGroup);
        return navigation;
    }

    @Before
    public abstract void initMockData();
}
