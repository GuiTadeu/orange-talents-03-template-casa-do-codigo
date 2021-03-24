package com.orange.bookstore.customer;

import com.orange.bookstore.state.State;
import com.orange.bookstore.state.StateRepository;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final StateRepository stateRepository;

    public CustomerController(CustomerRepository customerRepository, AddressRepository addressRepository, StateRepository stateRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.stateRepository = stateRepository;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CustomerForm form) {

        State state = stateRepository.getByName(form.getStateName());
        Address address = addressRepository.save(form.toAddress(state));

        Customer customer = form.toModel(address);
        customerRepository.save(customer);

        return ResponseEntity.ok().build();
    }
}
