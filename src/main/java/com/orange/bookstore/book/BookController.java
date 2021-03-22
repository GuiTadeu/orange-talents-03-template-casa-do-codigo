package com.orange.bookstore.book;

import com.orange.bookstore.author.Author;
import com.orange.bookstore.author.AuthorRepository;
import com.orange.bookstore.category.Category;
import com.orange.bookstore.category.CategoryRepository;
import com.orange.bookstore.share.FieldErrorsExtractor;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@Api
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity details(@PathVariable Long id) {
        Optional<Book> possibleBook = bookRepository.findById(id);

        if(possibleBook.isPresent()) {
            BookDetailsDTO detailsDTO = new BookDetailsDTO(possibleBook.get());
            return ResponseEntity.ok(detailsDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listAll")
    public ResponseEntity listAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody BookForm form, BindingResult result) {

        if (result.hasErrors())
            return ResponseEntity.badRequest().body(new FieldErrorsExtractor(result.getFieldErrors()));

        Category category = categoryRepository.getOne(form.getCategoryId());
        Author author = authorRepository.getOne(form.getAuthorId());

        Book book = form.toModel(category, author);
        bookRepository.save(book);

        return ResponseEntity.ok().build();
    }
}
