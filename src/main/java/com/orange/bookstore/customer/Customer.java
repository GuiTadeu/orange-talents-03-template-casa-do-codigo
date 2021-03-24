package com.orange.bookstore.customer;

import com.orange.bookstore.country.Country;
import com.orange.bookstore.state.State;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    private String document;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @NotNull
    @ManyToOne
    private Address address;

    @NotBlank
    private String phoneNumber;

    public Customer(@Email @NotBlank String email, @NotBlank String name, @NotBlank String lastname,
                    String document, PersonType personType, @NotNull Address address, @NotBlank String phoneNumber) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.document = document;
        this.personType = personType;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDocument() {
        return document;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public Address getAddress() {
        return address;
    }

    public String getStreet() {
        return address.getStreet();
    }

    public String getNumber() {
        return address.getNumber();
    }

    public String getCity() {
        return address.getCity();
    }

    public String getComplement() {
        return address.getComplement();
    }

    public String getPostalCode() {
        return address.getPostalCode();
    }

    public State getState() {
        return address.getState();
    }

    public Country getCountry() {
        return address.getCountry();
    }
}
