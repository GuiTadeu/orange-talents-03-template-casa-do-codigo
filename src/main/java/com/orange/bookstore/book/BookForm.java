package com.orange.bookstore.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orange.bookstore.author.Author;
import com.orange.bookstore.category.Category;
import com.orange.bookstore.share.MustExistOnDatabase;
import com.orange.bookstore.share.UniqueValue;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookForm implements Serializable {

    @NotBlank
    @UniqueValue(domainClass = Book.class, fieldName = "title")
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
    @UniqueValue(domainClass = Book.class, fieldName = "isbn")
    private String isbn;

    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @NotNull
    @MustExistOnDatabase(domainClass = Category.class, fieldName = "id")
    private Long categoryId;

    @NotNull
    @MustExistOnDatabase(domainClass = Author.class, fieldName = "id")
    private Long authorId;

    public BookForm(@NotBlank String title, @NotBlank @Size(max = 500) String resume, String summary,
                    @NotNull @Range(min = 20) BigDecimal price, @NotNull @Range(min = 100) Integer numberOfPages,
                    @Size(min = 10, max = 13) String isbn, @Future LocalDate publicationDate,
                    @NotNull Long categoryId, @NotNull Long authorId) {
        this.title = title;
        this.resume = resume;
        this.summary = summary;
        this.price = price;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.categoryId = categoryId;
        this.authorId = authorId;
    }

    public Book toModel(Category category, Author author) {
        return new Book(title, resume, summary, price, numberOfPages, isbn, publicationDate, category, author);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
