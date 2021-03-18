package com.orange.bookstore.author;

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
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody AuthorForm form, BindingResult result) {

        if (result.hasErrors())
            return ResponseEntity.badRequest().body(new FieldErrorsExtractor(result.getFieldErrors()));

        Author author = form.toModel();
        authorRepository.save(author);

        return ResponseEntity.ok().build();
    }

}
