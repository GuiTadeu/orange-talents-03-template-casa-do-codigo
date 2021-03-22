package com.orange.bookstore.country;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create__should_save_country_and_return_status_200_ok() throws Exception {

        URI uri = new URI("/countries");

        CountryForm form = new CountryForm();
        form.setName("Brasil");

        String json = new Gson().toJson(form);

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
    public void create__should_not_save_country_and_return_status_400_badRequest() throws Exception {

        URI uri = new URI("/countries");
        String json = new Gson().toJson(new CountryForm());

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