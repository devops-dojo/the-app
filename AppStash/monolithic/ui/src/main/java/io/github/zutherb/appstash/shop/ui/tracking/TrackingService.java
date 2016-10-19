package io.github.zutherb.appstash.shop.ui.tracking;

import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;

import java.util.List;

public interface TrackingService {
    void trackPurchase(OrderInfo order);
    void trackSignUp(UserInfo user);
    void trackLogin(UserInfo user);
    void trackProductView(List<ProductInfo> products, UserInfo user);
}
