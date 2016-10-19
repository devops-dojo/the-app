package io.github.zutherb.appstash.shop.repository.user.model;

import java.io.Serializable;

/**
 * @author zutherb
 */
public class Address implements Serializable {

    private static final int LONGITUDE_POS = 0;
    private static final int LATITUDE_POS = 1;

    private String street;
    private String zip;
    private String city;
    private String county;
    private String houseNumber;
    private String state;
    private String countryCode;
    private String country;

    //only recognize [ longitude, latitude ] ordering
    private double[] location = new double[2];

    public Address() {
    }

    public Address(String street,
            String zip,
            String city,
            String county,
            String houseNumber,
            String state,
            String countryCode,
            String country,
            double longitude,
            double latitude) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.county = county;
        this.houseNumber = houseNumber;
        this.state = state;
        this.countryCode = countryCode;
        this.country = country;
        setLongitude(longitude);
        setLatitude(latitude);
    }


    public Address(Address address) {
        this(address.getStreet(), address.getZip(), address.getCity(), address.getCounty(), address.getHouseNumber(),
                address.getState(), address.getCountryCode(), address.getCountry(), address.getLongitude(),
                address.getLatitude());
    }

    public Address(String street, String zip, String city, String houseNumber) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        return location[LONGITUDE_POS];
    }

    public void setLongitude(double longitude) {
        location[LONGITUDE_POS] = longitude;
    }

    public double getLatitude() {
        return location[LATITUDE_POS];
    }

    public void setLatitude(double latitude) {
        location[LATITUDE_POS] = latitude;
    }
}
