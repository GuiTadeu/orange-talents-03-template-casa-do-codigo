package com.orange.bookstore.category;

import com.orange.bookstore.share.FieldErrorsExtractor;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CategoryForm form, BindingResult result) {

        if (result.hasErrors())
            return ResponseEntity.badRequest().body(new FieldErrorsExtractor(result.getFieldErrors()));

        Category category = form.toModel();
        categoryRepository.save(category);

        return ResponseEntity.ok().build();
    }

}
