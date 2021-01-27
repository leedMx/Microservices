package com.udacity.pricing;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Price {
    @Id
    private Long vehicleId;
    private String currency;
    private Double price;
}
