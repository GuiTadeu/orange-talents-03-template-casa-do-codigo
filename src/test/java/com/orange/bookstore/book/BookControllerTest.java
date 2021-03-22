package com.orange.bookstore.book;

import com.orange.bookstore.author.Author;
import com.orange.bookstore.category.Category;
import org.junit.jupiter.api.BeforeEach;
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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager manager;

    private Category category;
    private Author author;

    @BeforeEach
    private void setup() {
        this.category = new Category("Fantasia");
        this.author = new Author("Cervantes", "cervantes@gmail.com", "Romancista, dramaturgo e poeta");

        manager.persist(category);
        manager.persist(author);
    }


    @Test
    @Transactional
    public void create__should_save_book_and_return_status_200_ok() throws Exception {
        URI uri = new URI("/books");
        String tenDaysLater = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String json = "{\"authorId\":" + author.getId() + ","
                + "\"categoryId\":" + category.getId() + ","
                + "\"isbn\":\"8595200858\","
                + "\"numberOfPages\":100,"
                + "\"price\":250.5,"
                + "\"publicationDate\":\"" + tenDaysLater + "\","
                + "\"resume\":\"Cavaleiro da Triste Figura\","
                + "\"summary\":\"1. Dois; 3. Quatro\","
                + "\"title\":\"Dom Quixote\"}";

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
    public void create__should_not_save_book_and_return_status_400_badRequest() throws Exception {
        URI uri = new URI("/books");

        String json = "{\"authorId\":0,"
                + "\"categoryId\":0,"
                + "\"isbn\":\"85952008581234567890\","
                + "\"numberOfPages\":0,"
                + "\"price\":0,"
                + "\"publicationDate\":\"1999-01-01\","
                + "\"resume\":\"\","
                + "\"summary\":\"\","
                + "\"title\":\"\"}";

        mockMvc
            .perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @Test
    @Transactional
    public void listAll__should_return_numberOfElements_0_and_return_status_200_ok() throws Exception {
        URI uri = new URI("/books/listAll");

        mockMvc
            .perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(200))
            .andExpect(jsonPath("$.numberOfElements").value("0"));
    }

    @Test
    @Transactional
    public void listAll__should_return_numberOfElements_2_and_return_status_200_ok() throws Exception {
        URI uri = new URI("/books/listAll");

        Book firstBook = new Book("Dom Quixote", "Cavaleiro da Triste Figura",
                "1. Dois; 3. Quatro", new BigDecimal("250.50"), 100,
                "8595200858", LocalDate.now().plusDays(10), category, author);

        Book secondBook = new Book("Sancho Pança", "O Governador",
                "1. Dois; 3. Quatro", new BigDecimal("250.50"), 100,
                "8292220252", LocalDate.now().plusDays(15), category, author);

        manager.persist(firstBook);
        manager.persist(secondBook);

        mockMvc
            .perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(200))
            .andExpect(jsonPath("$.numberOfElements").value("2"));
    }

    @Test
    @Transactional
    public void details__should_not_return_book_details_and_return_status_404_notFound() throws Exception {
        URI uri = new URI(String.format("/books/%s/details", 0L));

        mockMvc
            .perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(404));
    }

    @Test
    @Transactional
    public void details__should_return_book_details_and_status_200() throws Exception {
        Book book = new Book("Dom Quixote", "Cavaleiro da Triste Figura",
                "1. Dois; 3. Quatro", new BigDecimal("250.50"), 100,
                "8595200858", LocalDate.now().plusDays(10), category, author);

        manager.persist(book);

        URI uri = new URI(String.format("/books/%s/details", book.getId()));

        mockMvc
            .perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                .status()
                .is(200))
            .andExpect(jsonPath("$.title").value("Dom Quixote"))
            .andExpect(jsonPath("$.resume").value("Cavaleiro da Triste Figura"))
            .andExpect(jsonPath("$.summary").value("1. Dois; 3. Quatro"))
            .andExpect(jsonPath("$.numberOfPages").value(100))
            .andExpect(jsonPath("$.isbn").value("8595200858"))
            .andExpect(jsonPath("$.categoryName").value(category.getName()))
            .andExpect(jsonPath("$.authorName").value(author.getName()))
            .andExpect(jsonPath("$.authorDescription").value(author.getDescription()));

    }

}