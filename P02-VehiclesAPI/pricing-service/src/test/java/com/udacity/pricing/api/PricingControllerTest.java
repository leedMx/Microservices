package com.udacity.pricing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.udacity.pricing.domain.price.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PricingController.class)
class PricingControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    void getPriceTest() throws Exception {
        mvc.perform(get("/services/price?vehicleId={0}",1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPriceTestContent() throws Exception {
        MvcResult response = mvc.perform(
                get("/services/price?vehicleId={0}",1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Price price = mapper.readValue(
                response.getResponse().getContentAsString(), Price.class);

        assertEquals("USD", price.getCurrency());
        assertTrue(price.getPrice().doubleValue() > 0);
    }

    @Test
    void getPriceForNonExistentId() throws Exception {
        mvc.perform(get("/services/price?vehicleId={0}",21L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPriceForBadRequest() throws Exception {
        mvc.perform(get("/services/price"))
                .andExpect(status().isBadRequest());
    }
}