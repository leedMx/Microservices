package com.udacity.vehicles.client.prices;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Data
public class Price {

    private Long vehicleId;
    private String currency;
    private Double price;

}
