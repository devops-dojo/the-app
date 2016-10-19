package io.github.zutherb.appstash.shop.service.order.model;

import io.github.zutherb.appstash.shop.service.user.model.AddressInfo;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.shop.service.user.model.AddressInfo;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;

/**
 * @author zutherb
 */
public class DeliveryAddressInfo extends AddressInfo {
    
    private String firstname;
    private String lastname;

    public DeliveryAddressInfo() {
        super();
    }

    public DeliveryAddressInfo(UserInfo userInfo){
        super(userInfo.getAddress().getStreet(), userInfo.getAddress().getZip(), userInfo.getAddress().getCity(),
                userInfo.getAddress().getCounty(), userInfo.getAddress().getHouseNumber(), userInfo.getAddress().getState(),
                userInfo.getAddress().getCountryCode(), userInfo.getAddress().getCountry(), userInfo.getAddress().getLongitude(),
                userInfo.getAddress().getLatitude());
        firstname = userInfo.getFirstname();
        lastname = userInfo.getLastname();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
