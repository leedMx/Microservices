package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    @Test
    void getPrice() throws Exception {
        Price price = PricingService.getPrice(1L);
        assertTrue(price.getPrice().doubleValue() > 0.0);
    }

    @Test
    void getPriceException() throws Exception {
        assertThrows(PriceException.class,()->PricingService.getPrice(21L));
    }
}