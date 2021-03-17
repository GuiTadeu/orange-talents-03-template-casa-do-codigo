package com.orange.bookstore.author;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.orange.bookstore.config.FieldErrorsExtractor;
import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	private final AuthorRepository authorRepository;
	private final DuplicatedEmailValidator duplicatedEmailValidator;

	
	public AuthorController(AuthorRepository authorRepository, DuplicatedEmailValidator duplicatedEmailValidator) {
		this.authorRepository = authorRepository;
		this.duplicatedEmailValidator = duplicatedEmailValidator;
	}
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(duplicatedEmailValidator);
	}
	
	@PostMapping
	public ResponseEntity create(@Valid @RequestBody AuthorForm form, BindingResult result) {
		
		if(result.hasErrors())
			return ResponseEntity.badRequest().body(new FieldErrorsExtractor(result.getFieldErrors()));
		
		Author author = form.toModel();
		authorRepository.save(author);
		
		return ResponseEntity.ok().build();
	}

}
