package com.orange.bookstore.book;

import com.orange.bookstore.author.Author;
import com.orange.bookstore.category.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager manager;

    @Test
    void save__should_save_new_book() {

        Category category = new Category("Fantasia");
        Author author = new Author("Cervantes", "cervantes@gmail.com", "Romancista, dramaturgo e poeta");

        manager.persist(category);
        manager.persist(author);

        Book book = new Book("Dom Quixote", "Cavaleiro da Triste Figura",
                "1. Dois; 3. Quatro", new BigDecimal("250.50"), 100,
                "8595200858", LocalDate.now().plusDays(10), category, author);

        Long savedBookId = bookRepository.save(book).getId();
        Optional<Book> retrievedBook = bookRepository.findById(savedBookId);

        assertThat(retrievedBook).isNotEmpty();

        assertThat(retrievedBook.get().getCategory()).isEqualTo(category);
        assertThat(retrievedBook.get().getAuthor()).isEqualTo(author);

        assertThat(retrievedBook.get().getTitle()).isEqualTo("Dom Quixote");
        assertThat(retrievedBook.get().getResume()).isEqualTo("Cavaleiro da Triste Figura");
        assertThat(retrievedBook.get().getSummary()).isEqualTo("1. Dois; 3. Quatro");
        assertThat(retrievedBook.get().getPrice()).isEqualTo(new BigDecimal("250.50"));
        assertThat(retrievedBook.get().getNumberOfPages()).isEqualTo(100);
        assertThat(retrievedBook.get().getIsbn()).isEqualTo("8595200858");
        assertThat(retrievedBook.get().getPublicationDate()).isEqualTo(LocalDate.now().plusDays(10));
    }

    @Test
    void save__should_throw_exception_and_not_save_new_book() {

        Long savedBookId = 0L;

        try {
            Book book = new Book(EMPTY, EMPTY, EMPTY, null, null, EMPTY, null, null, null);
            savedBookId = bookRepository.save(book).getId();
        } catch (ConstraintViolationException ex) {
            assertThat(savedBookId).isEqualTo(0L);
        }

        assertThat(bookRepository.findById(savedBookId)).isEmpty();
    }
}