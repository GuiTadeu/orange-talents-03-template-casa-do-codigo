package com.orange.bookstore.state;

import com.orange.bookstore.country.Country;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @ManyToOne
    private Country country;

    public State(@NotBlank String name, @NotNull Country country) {
        this.name = name;
        this.country = country;
    }

    public State() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
