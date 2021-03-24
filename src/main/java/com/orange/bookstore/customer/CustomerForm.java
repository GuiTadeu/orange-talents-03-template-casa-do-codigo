package com.orange.bookstore.customer;

import com.orange.bookstore.country.Country;
import com.orange.bookstore.share.MustExistOnDatabase;
import com.orange.bookstore.share.UniqueValue;
import com.orange.bookstore.state.State;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.orange.bookstore.customer.PersonType.PF;
import static com.orange.bookstore.customer.PersonType.PJ;

@GroupSequenceProvider(value = PersonGroupSequenceProvider.class)
public class CustomerForm {

    @Email
    @NotBlank
    @UniqueValue(domainClass = Customer.class, fieldName = "email")
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @CPF(groups = PhysicalPersonGroup.class)
    @CNPJ(groups = LegalPersonGroup.class)
    @UniqueValue(domainClass = Customer.class, fieldName = "document")
    private String document;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String city;

    @NotBlank
    private String complement;

    @NotBlank
    private String postalCode;

    @NotBlank
    @MustExistOnDatabase(domainClass = Country.class, fieldName = "name")
    private String countryName;

    @NotBlank
    @MustExistOnDatabase(domainClass = State.class, fieldName = "name")
    private String stateName;

    public CustomerForm(@Email @NotBlank String email, @NotBlank String name, @NotBlank String lastname,
                        String document, PersonType personType, @NotBlank String phoneNumber, @NotBlank String street,
                        @NotBlank String number, @NotBlank String city, @NotBlank String complement,
                        @NotBlank String postalCode, @NotBlank String countryName, @NotBlank String stateName) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.document = document;
        this.personType = personType;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.number = number;
        this.city = city;
        this.complement = complement;
        this.postalCode = postalCode;
        this.countryName = countryName;
        this.stateName = stateName;
    }

    public Customer toModel(Address address) {
        return new Customer(email, name, lastname, document, personType, address, phoneNumber);
    }

    public Address toAddress(State state) {
        return new Address(street, number, city, complement, state, postalCode);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public boolean isPF() {
        return PF.equals(personType);
    }

    public boolean isPJ() {
        return PJ.equals(personType);
    }
}
