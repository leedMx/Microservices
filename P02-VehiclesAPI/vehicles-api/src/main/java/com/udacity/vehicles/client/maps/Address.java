package com.udacity.vehicles.client.maps;

import lombok.*;

/**
 * Declares a class to store an address, city, state and zip code.
 */
@Data
public class Address {

    private String address;
    private String city;
    private String state;
    private String zip;

    public Address() {
    }

}
