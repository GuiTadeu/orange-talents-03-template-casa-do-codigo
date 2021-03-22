package com.orange.bookstore.state;

import com.google.gson.Gson;
import com.orange.bookstore.country.Country;
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

import static org.apache.logging.log4j.util.Strings.EMPTY;

@SpringBootTest
@AutoConfigureMockMvc
class StateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager manager;

    @Test
    @Transactional
    public void create__should_save_state_and_return_status_200_ok() throws Exception {

        URI uri = new URI("/states");

        manager.persist(new Country("Brasil"));
        StateForm form = new StateForm("SP", "Brasil");
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
    public void create__should_not_save_state_and_return_status_400_badRequest() throws Exception {

        URI uri = new URI("/states");
        String json = new Gson().toJson(new StateForm(EMPTY, EMPTY));

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