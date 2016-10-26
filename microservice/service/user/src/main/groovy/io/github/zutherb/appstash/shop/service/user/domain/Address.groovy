package io.github.zutherb.appstash.shop.service.user.domain

import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * @author zutherb
 */
@JsonSerialize
class Address implements Serializable {
    private String street;
    private String zip;
    private String city;
    private String county;
    private String houseNumber;
    private String state;
    private String countryCode;
    private String country;
}
