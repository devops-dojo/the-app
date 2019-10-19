package io.github.zutherb.appstash.shop.ui.page;

import io.github.zutherb.appstash.shop.ui.AbstractWicketTest;
import io.github.zutherb.appstash.shop.ui.page.user.LoginPage;
import org.junit.Test;
import org.junit.Ignore;

/**
 * @author zutherb
 */
public class LoginPageTest extends AbstractWicketTest {

    @Ignore("not ready yet") @Test
    public void testRenderPage(){
        wicketTester.startPage(LoginPage.class);
        wicketTester.assertRenderedPage(LoginPage.class);
    }

    @Override
    public void initMockData() {
    }
}
