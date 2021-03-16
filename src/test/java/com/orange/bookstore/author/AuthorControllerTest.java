package com.orange.bookstore.author;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class AuthorControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
    public void create__should_save_author_and_return_status_200_ok() throws Exception {
		
		URI uri = new URI("/authors");
		String json = new Gson().toJson(new Author("Edgar Allan Poe", "edgar@gmail.com", "Autor, poeta, editor"));
		
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
    public void create__should_not_save_author_and_return_status_400_badRequest() throws Exception {
		
		URI uri = new URI("/authors");
		String json = new Gson().toJson(new Author("", "", ""));
		
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
