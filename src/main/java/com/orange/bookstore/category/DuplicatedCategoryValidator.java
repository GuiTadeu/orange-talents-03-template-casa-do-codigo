package com.orange.bookstore.category;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DuplicatedCategoryValidator implements Validator {
	
	private final CategoryRepository categoryRepository;
	
	public DuplicatedCategoryValidator(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return CategoryForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CategoryForm form = (CategoryForm) target;
		
		Optional<Category> optionalCategory = categoryRepository.findByName(form.getName());
		
		if(optionalCategory.isPresent()) {
			errors.rejectValue("name", null, "Categoria já cadastrada!");
		}
	}
}
