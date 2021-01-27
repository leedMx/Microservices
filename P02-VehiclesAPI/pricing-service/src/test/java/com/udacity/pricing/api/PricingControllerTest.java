package com.udacity.pricing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing.entity.Price;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PricingControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @LocalServerPort
    private static int port;
    private static String url;

    @BeforeAll
    static void setUp() {
        url = "http://localhost:" + port;
    }

    @Test
    void getPriceTest() throws Exception {
        mvc.perform(
                get(url + "/prices/{0}",1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllPricesTest() throws Exception {
        mvc.perform(get(url + "/prices")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPriceTestContent() throws Exception {
        MvcResult response = mvc.perform(
                get(url + "/prices/{0}",1L))
                .andExpect(status().isOk())
                .andReturn();
        String content = response.getResponse().getContentAsString();
        Price price = mapper.readValue(content, Price.class);

        assertEquals("USD", price.getCurrency());
        assertTrue(price.getPrice().doubleValue() > 0);
    }

    @Test
    void getPriceForNonExistentId() throws Exception {
        MvcResult result = mvc.perform(
                get(url + "/prices/{0}", 21L))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception e = result.getResolvedException();
        assertTrue( e instanceof ResourceNotFoundException);
    }

}