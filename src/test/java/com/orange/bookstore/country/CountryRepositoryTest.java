package com.orange.bookstore.country;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void getByName__should_return_country() {
        Country country = new Country("Brasil");

        countryRepository.save(country);
        Country retrievedCountry = countryRepository.getByName("Brasil");

        assertThat(retrievedCountry).isNotNull();
        assertThat(retrievedCountry.getName()).isEqualTo("Brasil");
    }

    @Test
    void getByName__should_not_return_country() {
        Country retrievedCountry = countryRepository.getByName("Cthulhu");
        assertThat(retrievedCountry).isNull();
    }

}