package com.orange.bookstore.state;

import com.orange.bookstore.country.Country;
import com.orange.bookstore.country.CountryRepository;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping("/states")
public class StateController {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    public StateController(StateRepository stateRepository, CountryRepository countryRepository) {
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody StateForm form) {
        Country country = countryRepository.getByName(form.getCountryName());
        State state = form.toModel(country);
        stateRepository.save(state);

        return ResponseEntity.ok().build();
    }
}
