package com.orange.bookstore.book;

import com.orange.bookstore.author.Author;
import com.orange.bookstore.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager manager;

    private Category category;
    private Author author;

    private Book firstBook;
    private Book secondBook;

    @BeforeEach
    private void setup() {
        this.category = new Category("Fantasia");
        this.author = new Author("Cervantes", "cervantes@gmail.com", "Romancista, dramaturgo e poeta");

        manager.persist(category);
        manager.persist(author);

        firstBook = new Book("Dom Quixote", "Cavaleiro da Triste Figura",
                "1. Dois; 3. Quatro", new BigDecimal("250.50"), 100,
                "8595200858", LocalDate.now().plusDays(10), category, author);

        secondBook = new Book("Sancho Pança", "O Governador",
                "1. Dois; 3. Quatro", new BigDecimal("250.50"), 100,
                "8292220252", LocalDate.now().plusDays(15), category, author);

        manager.persist(firstBook);
        manager.persist(secondBook);
    }

    @Test
    void save__should_save_new_book() {

        Long savedFirstBookId = firstBook.getId();
        Optional<Book> retrievedBook = bookRepository.findById(savedFirstBookId);

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

    @Test
    void findAll__should_return_page_of_books() {
        assertThat(bookRepository.findAll()).hasSize(2);

        Pageable pageable = PageRequest.of(0,1);
        assertThat(bookRepository.findAll(pageable)).hasSize(1);
    }

    @Test
    void findAll__should_return_page_of_books_sort_by_title_desc() {
        Pageable pageable = PageRequest.of(0,2, Sort.by("title").descending());
        Page<Book> listAll = bookRepository.findAll(pageable);
        List<String> listTitles = listAll.get().map(Book::getTitle).collect(Collectors.toList());

        assertThat(bookRepository.findAll(pageable)).hasSize(2);
        assertThat(listTitles.get(0)).isEqualTo("Sancho Pança");
        assertThat(listTitles.get(1)).isEqualTo("Dom Quixote");
    }
}