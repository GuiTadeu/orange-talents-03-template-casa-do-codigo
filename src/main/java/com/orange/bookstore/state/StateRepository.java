package com.orange.bookstore.state;

import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Long> {

    State getByName(String name);
}
