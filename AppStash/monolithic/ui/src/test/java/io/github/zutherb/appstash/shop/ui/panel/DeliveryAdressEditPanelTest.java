package io.github.zutherb.appstash.shop.ui.panel;


import io.github.zutherb.appstash.shop.service.order.model.DeliveryAddressInfo;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.service.order.model.OrderItemInfo;
import io.github.zutherb.appstash.shop.service.user.model.AddressInfo;
import io.github.zutherb.appstash.shop.service.user.model.RoleInfo;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.shop.ui.service.TestDataFactory;
import io.github.zutherb.appstash.shop.ui.AbstractWicketTest;
import org.apache.wicket.model.Model;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;

/**
 * @author zutherb
 */
public class DeliveryAdressEditPanelTest extends AbstractWicketTest {

    @Test
    @Ignore
    public void testRender() {
        wicketTester.startComponentInPage(new DeliveryAdressEditPanel("panel", Model.of(createOrderInfo())));
        //wicketTester.assertComponent("panel", DeliveryAdressEditPanel.class);
    }

    private OrderInfo createOrderInfo() {
        return new OrderInfo(createUserInfo(), new DeliveryAddressInfo(), Collections.singletonList(createOrderItemInfo()), "");
    }

    private OrderItemInfo createOrderItemInfo() {
        return TestDataFactory.createOrderItem();
    }

    private UserInfo createUserInfo() {
        return new UserInfo("bzuther", "comSystoRockz", Collections.singleton(new RoleInfo("admin")),
                new AddressInfo("Lindwurmstraße 97", "80337", "München", null, null, null, null, null, 0.0, 0.0));
    }

    @Override
    public void initMockData() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
