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
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

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
    void save__should_save_new_address() {
        Address address = new Address("Rua dos bobos", "0", "Diadema", "Casa 1", state, "04474200");

        Long savedAddressId = addressRepository.save(address).getId();
        Optional<Address> retrievedAddress = addressRepository.findById(savedAddressId);

        assertThat(retrievedAddress).isNotEmpty();
        assertThat(retrievedAddress.get().getStreet()).isEqualTo("Rua dos bobos");
        assertThat(retrievedAddress.get().getNumber()).isEqualTo("0");
        assertThat(retrievedAddress.get().getCity()).isEqualTo("Diadema");
        assertThat(retrievedAddress.get().getComplement()).isEqualTo("Casa 1");
        assertThat(retrievedAddress.get().getState()).isEqualTo(state);
        assertThat(retrievedAddress.get().getPostalCode()).isEqualTo("04474200");
    }

    @Test
    void save__should_throw_exception_and_not_save_new_address() {

        Long savedAddressId = 0L;

        try {
            Address address = new Address(EMPTY, EMPTY, EMPTY, EMPTY, null, EMPTY);
            savedAddressId = addressRepository.save(address).getId();
        } catch(ConstraintViolationException ex) {
            assertThat(savedAddressId).isEqualTo(0L);
        }

        assertThat(addressRepository.findById(savedAddressId)).isEmpty();
    }

}