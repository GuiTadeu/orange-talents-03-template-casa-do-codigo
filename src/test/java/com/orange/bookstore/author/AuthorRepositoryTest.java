package com.orange.bookstore.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
class AuthorRepositoryTest {
	
	@Autowired
	private AuthorRepository authorRepository;

	@Test
	void save__should_save_new_author() {
		Author author = new Author("Edgar Allan Poe", "edgar@gmail.com", "Autor, poeta, editor");
		
		Long savedAuthorId = authorRepository.save(author).getId();
		Optional<Author> retrievedAuthor = authorRepository.findById(savedAuthorId);
		
		assertThat(retrievedAuthor).isNotEmpty();
		assertThat(retrievedAuthor.get().getName()).isEqualTo("Edgar Allan Poe");
		assertThat(retrievedAuthor.get().getEmail()).isEqualTo("edgar@gmail.com");
		assertThat(retrievedAuthor.get().getDescription()).isEqualTo("Autor, poeta, editor");
	}
	
	@Test
	void save__should_throw_exception_and_not_save_new_author() {
		
		Long savedAuthorId = 0L;
		
		try {
			Author author = new Author("", "", "");
			savedAuthorId = authorRepository.save(author).getId();
		} catch(ConstraintViolationException ex) {
			assertThat(savedAuthorId).isEqualTo(0L);
		}
		
		assertThat(authorRepository.findById(savedAuthorId)).isEmpty();
	}
	
	@Test
	void findByEmail__should_return_author() {
		Author author = new Author("Edgar Allan Poe", "edgar@gmail.com", "Autor, poeta, editor");
		
		authorRepository.save(author);
		Optional<Author> retrievedAuthor = authorRepository.findByEmail("edgar@gmail.com");
		
		assertThat(retrievedAuthor).isNotEmpty();
		assertThat(retrievedAuthor.get().getName()).isEqualTo("Edgar Allan Poe");
		assertThat(retrievedAuthor.get().getEmail()).isEqualTo("edgar@gmail.com");
		assertThat(retrievedAuthor.get().getDescription()).isEqualTo("Autor, poeta, editor");
	}
	
	@Test
	void findByEmail__should_not_return_author() {
		Optional<Author> retrievedAuthor = authorRepository.findByEmail("cthulhu2077@gmail.com");
		assertThat(retrievedAuthor).isEmpty();
	}
}
