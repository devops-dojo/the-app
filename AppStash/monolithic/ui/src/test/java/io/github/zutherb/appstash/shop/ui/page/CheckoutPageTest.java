package io.github.zutherb.appstash.shop.ui.page;

import io.github.zutherb.appstash.shop.ui.AbstractWicketTest;
import io.github.zutherb.appstash.shop.ui.page.checkout.CheckoutPage;
import org.junit.Ignore;
import org.junit.Test;

import static io.github.zutherb.appstash.shop.ui.service.TestDataFactory.createOrderItems;
import static org.mockito.Mockito.when;

/**
 * @author zutherb
 */
public class CheckoutPageTest extends AbstractWicketTest {

    @Test
    @Ignore
    public void testPageRending(){
        wicketTester.startPage(CheckoutPage.class);
        wicketTester.assertRenderedPage(CheckoutPage.class);
    }

    @Override
    public void initMockData() {
        when(authenticationService.isAuthorized()).thenReturn(true);
        when(cart.isEmpty()).thenReturn(false);
        when(checkout.getOrderItemInfos()).thenReturn(createOrderItems());
    }
}
