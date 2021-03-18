package com.orange.bookstore.category;

import com.orange.bookstore.share.UniqueValue;

import javax.validation.constraints.NotBlank;

public class CategoryForm {

    @NotBlank
    @UniqueValue(domainClass = Category.class, fieldName = "name")
    private String name;

    public Category toModel() {
        return new Category(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
