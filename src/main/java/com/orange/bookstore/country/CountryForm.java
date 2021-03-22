package com.orange.bookstore.country;

import com.orange.bookstore.share.UniqueValue;

import javax.validation.constraints.NotBlank;

public class CountryForm {

    @NotBlank
    @UniqueValue(domainClass = Country.class, fieldName = "name")
    private String name;

    public Country toModel() {
        return new Country(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
