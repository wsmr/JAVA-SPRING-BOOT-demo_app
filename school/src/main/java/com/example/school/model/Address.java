package com.example.school.model;

import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Address {

    @Field("street")
    @NotBlank(message = "Street address is required")
    private String street;

    @Field("city")
    @NotBlank(message = "City is required")
    private String city;

    @Field("state_province")
    private String stateProvince;

    @Field("postal_code")
    @Size(min = 5, max = 10, message = "Postal code must be between 5 and 10 characters")
    private String postalCode;

    @Field("country")
    @NotBlank(message = "Country is required")
    private String country;

    // Constructors
    public Address() {}

    public Address(String street, String city, String stateProvince,
                   String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.stateProvince = stateProvince;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and Setters
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}