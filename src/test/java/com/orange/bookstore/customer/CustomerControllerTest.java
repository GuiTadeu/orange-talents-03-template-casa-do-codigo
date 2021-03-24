package com.orange.bookstore.customer;

import com.orange.bookstore.country.Country;
import com.orange.bookstore.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager manager;

    private Country country;
    private State state;

    @BeforeEach
    private void setup() {
        this.country = new Country("Brasil");
        this.state = new State("São Paulo", country);

        manager.persist(country);
        manager.persist(state);
    }

    @Test
    @Transactional
    public void create__should_save_customer_and_return_status_200_ok() throws Exception {
        URI uri = new URI("/customers");

        String json = "{\"city\": \"Diadema\","
                + "\"complement\": \"Casa 1\","
                + "\"countryName\": \"Brasil\","
                + "\"document\": \"28787254093\","
                + "\"email\": \"jubileu@gmail.com\","
                + "\"lastname\": \"Silva\","
                + "\"name\": \"Jubileu\","
                + "\"number\": \"0\","
                + "\"personType\": \"PF\","
                + "\"phoneNumber\": \"123456789\","
                + "\"postalCode\": \"04474222\","
                + "\"stateName\": \"São Paulo\","
                + "\"street\": \"Rua dos bobos\"}";
        mockMvc
            .perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(200));
    }

    @Test
    @Transactional
    public void create__should_not_save_customer_and_return_status_400_badRequest() throws Exception {
        URI uri = new URI("/customers");

        String json = "{\"city\": \"Diadema\","
                + "\"complement\": \"Casa 1\","
                + "\"countryName\": \"Brasil\","
                + "\"document\": \"INVALID\","
                + "\"email\": \"INVALID\","
                + "\"lastname\": \"Silva\","
                + "\"name\": \"Jubileu\","
                + "\"number\": \"0\","
                + "\"personType\": \"INVALID\","
                + "\"phoneNumber\": \"123456789\","
                + "\"postalCode\": \"04474222\","
                + "\"stateName\": \"INVALID\","
                + "\"street\": \"Rua dos bobos\"}";

        mockMvc
            .perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

}