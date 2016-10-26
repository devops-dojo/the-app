package io.github.zutherb.appstash.shop.service.user.model;

import java.io.Serializable;

/**
 * @author zutherb
 */
public class AddressInfo implements Serializable {
    private String  street;
    private String  zip;
    private String  city;
    private String  county;
    private String  houseNumber;
    private String  state;
    private String  countryCode;
    private String  country;
    private double  longitude;
    private double  latitude;

    protected AddressInfo(){}

    public AddressInfo(String street, String zip, String city, String  county, String  houseNumber, String  state,
                       String  countryCode, String  country, double longitude, double latitude) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.county = county;
        this.houseNumber = houseNumber;
        this.state = state;
        this.countryCode = countryCode;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
