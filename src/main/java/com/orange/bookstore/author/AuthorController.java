package com.orange.bookstore.author;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	private final AuthorRepository authorRepository;
	
	public AuthorController(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void create(@Valid @RequestBody AuthorForm form) {
		Author author = form.toModel();
		authorRepository.save(author);
	}

}
