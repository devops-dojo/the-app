package io.github.zutherb.appstash.shop.repository.order.model;

import io.github.zutherb.appstash.shop.repository.user.model.Address;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.shop.repository.user.model.Address;
import io.github.zutherb.appstash.shop.repository.user.model.User;

/**
 * @author zutherb
 */
public class DeliveryAddress extends Address {

    private String firstname;
    private String lastname;

    public DeliveryAddress() {
    }

    public DeliveryAddress(Address address) {
        super(address.getStreet(), address.getZip(), address.getCity(), address.getCounty(), address.getHouseNumber(),
                address.getState(), address.getCountryCode(), address.getCountry(), address.getLongitude(),
                address.getLatitude());
    }


    public DeliveryAddress(User user, Address address) {
        super(address.getStreet(), address.getZip(), address.getCity(), address.getCounty(), address.getHouseNumber(),
                address.getState(), address.getCountryCode(), address.getCountry(), address.getLongitude(),
                address.getLatitude());
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
