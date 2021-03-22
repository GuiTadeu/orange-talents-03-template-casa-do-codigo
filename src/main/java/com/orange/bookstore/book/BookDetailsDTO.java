package com.orange.bookstore.book;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDetailsDTO {

    @NotBlank
    private String title;

    @NotBlank
    @Size(max=500)
    private String resume;

    private String summary;

    @NotNull
    @Range(min=100)
    private Integer numberOfPages;

    @NotBlank
    @Size(min=10, max=13)
    private String isbn;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String authorName;

    @NotBlank
    @Size(max=400)
    private String authorDescription;

    public BookDetailsDTO(@NotNull Book book) {
        this.title = book.getTitle();
        this.resume = book.getResume();
        this.summary = book.getSummary();
        this.numberOfPages = book.getNumberOfPages();
        this.isbn = book.getIsbn();
        this.categoryName = book.getCategory().getName();
        this.authorName = book.getAuthor().getName();
        this.authorDescription = book.getAuthor().getDescription();
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

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }
}
