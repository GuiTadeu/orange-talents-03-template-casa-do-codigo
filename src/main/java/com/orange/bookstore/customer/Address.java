package com.orange.bookstore.customer;

import com.orange.bookstore.country.Country;
import com.orange.bookstore.state.State;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String city;

    @NotBlank
    private String complement;

    @NotNull
    @ManyToOne
    private State state;

    @NotBlank
    private String postalCode;

    public Address(@NotBlank String street, @NotBlank String number, @NotBlank String city,
                   @NotBlank String complement, @NotNull State state, @NotBlank String postalCode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.complement = complement;
        this.state = state;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getComplement() {
        return complement;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public State getState() {
        return this.state;
    }

    public Country getCountry() {
        return state.getCountry();
    }
}
