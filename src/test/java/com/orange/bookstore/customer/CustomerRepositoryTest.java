package com.orange.bookstore.customer;

import com.orange.bookstore.country.Country;
import com.orange.bookstore.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager manager;

    private Country country;
    private State state;

    @BeforeEach
    private void setup() {
        this.country = new Country("Brasil");
        this.state = new State("São Paulo", country);

        manager.persist(country);
        manager.persist(state);
    }

    @Test
    void save__should_save_new_customer() {

        Address address = new Address("Rua dos bobos", "0", "Diadema", "Casa 1", state, "04474200");
        manager.persist(address);

        Customer customer = new Customer("jubileu@gmail.com", "Jubileu", "Silva", "28787254093", PersonType.PF, address, "2345678");

        Long savedCustomerId = customerRepository.save(customer).getId();
        Optional<Customer> retrievedCustomer = customerRepository.findById(savedCustomerId);

        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get().getEmail()).isEqualTo("jubileu@gmail.com");
        assertThat(retrievedCustomer.get().getName()).isEqualTo("Jubileu");
        assertThat(retrievedCustomer.get().getLastname()).isEqualTo("Silva");
        assertThat(retrievedCustomer.get().getDocument()).isEqualTo("28787254093");
        assertThat(retrievedCustomer.get().getPersonType()).isEqualTo(PersonType.PF);

        assertThat(retrievedCustomer.get().getStreet()).isEqualTo("Rua dos bobos");
        assertThat(retrievedCustomer.get().getNumber()).isEqualTo("0");
        assertThat(retrievedCustomer.get().getCity()).isEqualTo("Diadema");
        assertThat(retrievedCustomer.get().getComplement()).isEqualTo("Casa 1");
        assertThat(retrievedCustomer.get().getState()).isEqualTo(state);
        assertThat(retrievedCustomer.get().getPostalCode()).isEqualTo("04474200");
    }

    @Test
    void save__should_throw_exception_and_not_save_new_customer() {
        Long savedCustomerId = 0L;

        try {
            Customer customer = new Customer(EMPTY, EMPTY, EMPTY, EMPTY, null, null, EMPTY);
            savedCustomerId = customerRepository.save(customer).getId();
        } catch (ConstraintViolationException ex) {
            assertThat(savedCustomerId).isEqualTo(0L);
        }

        assertThat(customerRepository.findById(savedCustomerId)).isEmpty();
    }

}