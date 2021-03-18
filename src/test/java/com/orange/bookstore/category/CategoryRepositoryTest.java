package com.orange.bookstore.category;

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
class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void save__should_save_new_category() {
		Category category = new Category("Dark Fantasy");
		
		Long savedCategoryId = categoryRepository.save(category).getId();
		Optional<Category> retrievedCategory = categoryRepository.findById(savedCategoryId);
		
		assertThat(retrievedCategory).isNotEmpty();
		assertThat(retrievedCategory.get().getName()).isEqualTo("Dark Fantasy");
	}
	
	@Test
	void save__should_throw_exception_and_not_save_new_category() {
		
		Long savedCategoryId = 0L;
		
		try {
			Category category = new Category("");
			savedCategoryId = categoryRepository.save(category).getId();
		} catch(ConstraintViolationException ex) {
			assertThat(savedCategoryId).isEqualTo(0L);
		}
		
		assertThat(categoryRepository.findById(savedCategoryId)).isEmpty();
	}
	
	@Test
	void findByName__should_return_category() {
		Category category = new Category("Dark Fantasy");
		
		categoryRepository.save(category);
		Optional<Category> retrievedCategory = categoryRepository.findByName("Dark Fantasy");
		
		assertThat(retrievedCategory).isNotEmpty();
		assertThat(retrievedCategory.get().getName()).isEqualTo("Dark Fantasy");
	}
	
	@Test
	void findByName__should_not_return_category() {
		Optional<Category> retrievedCategory = categoryRepository.findByName("Cthulhu 2077");
		assertThat(retrievedCategory).isEmpty();
	}
}
