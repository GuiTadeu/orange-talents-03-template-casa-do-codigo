package com.orange.bookstore.author;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DuplicatedEmailValidator implements Validator {
	
	private final AuthorRepository authorRepository;
	
	public DuplicatedEmailValidator(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return AuthorForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AuthorForm form = (AuthorForm) target;
		
		Optional<Author> optionalAuthor = authorRepository.findByEmail(form.getEmail());
		
		if(optionalAuthor.isPresent()) {
			errors.rejectValue("email", null, "Email já cadastrado!");
		}
	}
}
