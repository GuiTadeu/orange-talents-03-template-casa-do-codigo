package com.orange.bookstore.category;

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
@RequestMapping("/categories")
public class CategoryController {
	
	private final CategoryRepository categoryRepository;
	private final DuplicatedCategoryValidator duplicatedCategoryValidator;
	
	public CategoryController(CategoryRepository categoryRepository, DuplicatedCategoryValidator duplicatedCategoryValidator) {
		this.categoryRepository = categoryRepository;
		this.duplicatedCategoryValidator = duplicatedCategoryValidator;
	}
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(duplicatedCategoryValidator);
	}
	
	@PostMapping
	public ResponseEntity create(@Valid @RequestBody CategoryForm form, BindingResult result) {
		
		if(result.hasErrors())
			return ResponseEntity.badRequest().body(new FieldErrorsExtractor(result.getFieldErrors()));
		
		Category category = form.toModel();
		categoryRepository.save(category);
		
		return ResponseEntity.ok().build();
	}

}
