package com.exuberant.bookmyflight.model;

/**
 * Created by rakesh on 23-Oct-2017.
 */
public class Location {
    private String address;
    private String city;
    private String country;

    public Location() {
    }

    public Location(String address, String city, String country) {
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
