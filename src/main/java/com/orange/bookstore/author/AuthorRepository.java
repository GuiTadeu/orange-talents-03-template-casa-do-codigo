package com.orange.bookstore.author;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

	Optional<Author> findByEmail(String email);
	
}
