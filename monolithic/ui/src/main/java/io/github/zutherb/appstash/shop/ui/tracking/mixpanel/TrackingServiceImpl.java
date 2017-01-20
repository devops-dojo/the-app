package io.github.zutherb.appstash.shop.ui.tracking.mixpanel;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import io.github.zutherb.appstash.common.util.Config;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.shop.ui.tracking.TrackingService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrackingServiceImpl implements TrackingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingServiceImpl.class);
    private static final boolean doTracking = (Config.getProperty("DO_TRACKING")!=null && Config.getProperty("DO_TRACKING").equalsIgnoreCase("TRUE"));

    private MessageBuilder messageBuilder;

    @Autowired
    public TrackingServiceImpl(@Value("${mix.panel.token}") String token) {
        messageBuilder = new MessageBuilder(token);
    }

    @Override
    public void trackPurchase(OrderInfo order) {
        
        if (doTracking && order!=null && order.getUser()!=null){
            try {
                ClientDelivery delivery = mapToPurchaseDelivery(order);

                JSONObject user = mapToUserJSONObject(order.getUser());

                MixpanelAPI mixpanel = new MixpanelAPI();
                mixpanel.deliver(delivery);
                mixpanel.sendMessage(user);
            } catch (Exception e) {
                LOGGER.error("Error occurred while tacking", e);
            }
        }
    }

    private JSONObject mapToUserJSONObject(UserInfo userInfo) throws JSONException {
        JSONObject properties = new JSONObject();
        properties.put("$email", userInfo.getEmail());
        properties.put("$first_name", userInfo.getFirstname());
        properties.put("$last_name", userInfo.getLastname());
        properties.put("$username", userInfo.getUsername());
        return messageBuilder.set(userInfo.getUsername(), properties);
    }

    @Override
    public void trackSignUp(UserInfo user) {
        if (doTracking && user!=null){
            try {
                JSONObject properties = new JSONObject();
                properties.put("username", user.getUsername());
                JSONObject signUp = messageBuilder.event(user.getUsername(), "Sign Up", properties);

                ClientDelivery delivery = new ClientDelivery();
                delivery.addMessage(signUp);

                JSONObject userJSONObject = mapToUserJSONObject(user);
                userJSONObject.put("$created", new Date());

                MixpanelAPI mixpanel = new MixpanelAPI();
                mixpanel.deliver(delivery);
                mixpanel.sendMessage(userJSONObject);
            } catch (Exception e) {
                LOGGER.error("Error occurred while tacking", e);
            }
        }    
    }

    @Override
    public void trackLogin(UserInfo user) {
        if (doTracking && user!=null){
            try {
                JSONObject properties = new JSONObject();
                properties.put("username", user.getUsername());
                JSONObject signUp = messageBuilder.event(user.getUsername(), "Login", properties);

                ClientDelivery delivery = new ClientDelivery();
                delivery.addMessage(signUp);

                JSONObject userJSONObject = mapToUserJSONObject(user);
                userJSONObject.put("$last_login", new Date());

                MixpanelAPI mixpanel = new MixpanelAPI();
                mixpanel.deliver(delivery);
                mixpanel.sendMessage(userJSONObject);
            } catch (Exception e) {
                LOGGER.error("Error occurred while tacking", e);
            }
        }
    }

    @Override
    public void trackProductView(List<ProductInfo> products, UserInfo user) {        
        JSONObject properties = new JSONObject();
        if (doTracking && products!=null && user!=null){
            try {
                properties.put("products", products.stream().map(productInfo -> productInfo.getName()).collect(Collectors.toList()));
                JSONObject view = messageBuilder.event(user.getUsername(), "View", properties);

                ClientDelivery delivery = new ClientDelivery();
                delivery.addMessage(view);

                MixpanelAPI mixpanel = new MixpanelAPI();
                mixpanel.deliver(delivery);
                mixpanel.sendMessage(mapToUserJSONObject(user));
            } catch (Exception e) {
                LOGGER.error("Error occurred while tacking ", e);
                LOGGER.error("user.getUsername(): ", user.getUsername());
                LOGGER.error("properties: ", properties.toString());
            }
        }
    }

    private ClientDelivery mapToPurchaseDelivery(OrderInfo order) throws JSONException {
        JSONObject properties = new JSONObject();
        properties.put("totel", order.getTotalSum().doubleValue());
        JSONObject purchase = messageBuilder.event(order.getOrderId().toString(), "Purchase", properties);

        ClientDelivery delivery = new ClientDelivery();
        delivery.addMessage(purchase);
        return delivery;
    }
}
