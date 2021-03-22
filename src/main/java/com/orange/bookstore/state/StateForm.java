package com.orange.bookstore.state;

import com.orange.bookstore.country.Country;
import com.orange.bookstore.share.MustExistOnDatabase;
import com.orange.bookstore.share.UniqueStateOnCountry;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@UniqueStateOnCountry
public class StateForm {

    @NotBlank
    private String name;

    @NotBlank
    @MustExistOnDatabase(domainClass = Country.class, fieldName = "name")
    private String countryName;

    public StateForm(@NotBlank String name, @NotNull String countryName) {
        this.name = name;
        this.countryName = countryName;
    }

    public State toModel(Country country) {
        return new State(name, country);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
