package com.orange.bookstore.book;

import com.orange.bookstore.author.Author;
import com.orange.bookstore.category.Category;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String resume;

    private String summary;

    @NotNull
    @Range(min = 20)
    private BigDecimal price;

    @NotNull
    @Range(min = 100)
    private Integer numberOfPages;

    @Size(min = 10, max = 13)
    @Column(unique = true)
    private String isbn;

    @Future
    private LocalDate publicationDate;

    @NotNull
    @ManyToOne
    private Category category;

    @NotNull
    @ManyToOne
    private Author author;

    public Book(@NotBlank String title, @NotBlank @Size(max = 500) String resume, String summary,
                @NotNull @Range(min = 20) BigDecimal price, @NotNull @Range(min = 100) Integer numberOfPages,
                @Size(min = 10, max = 13) String isbn, @Future LocalDate publicationDate,
                @NotNull Category category, @NotNull Author author) {
        this.title = title;
        this.resume = resume;
        this.summary = summary;
        this.price = price;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.category = category;
        this.author = author;
    }

    public Book() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getResume() {
        return resume;
    }

    public String getSummary() {
        return summary;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Category getCategory() {
        return category;
    }

    public Author getAuthor() {
        return author;
    }
}
