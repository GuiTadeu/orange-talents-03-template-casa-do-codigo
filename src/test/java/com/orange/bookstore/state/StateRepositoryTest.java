package com.orange.bookstore.state;

import com.orange.bookstore.country.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
class StateRepositoryTest {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private EntityManager manager;

    private Country country;

    @BeforeEach
    private void setup() {
        this.country = new Country("Brasil");
        manager.persist(country);
    }

    @Test
    void getByName__should_return_state() {
        State state = new State("São Paulo", country);

        stateRepository.save(state);
        State retrievedState = stateRepository.getByName("São Paulo");

        assertThat(retrievedState).isNotNull();
        assertThat(retrievedState.getName()).isEqualTo("São Paulo");
    }

    @Test
    void getByName__should_not_return_state() {
        State retrievedState = stateRepository.getByName("Cthulhu 2077");
        assertThat(retrievedState).isNull();
    }

}