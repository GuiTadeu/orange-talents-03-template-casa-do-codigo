package com.orange.bookstore.share;

import com.orange.bookstore.state.StateForm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueStateOnCountryValidator implements ConstraintValidator<UniqueStateOnCountry, Object> {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Query query = manager.createQuery("SELECT 1 FROM State st JOIN st.country ct WHERE st.name = :stateName AND ct.name = :countryName");

        StateForm stateForm = (StateForm) object;
        query.setParameter("stateName", stateForm.getName());
        query.setParameter("countryName", stateForm.getCountryName());
        Optional<?> result = query.getResultList().stream().findFirst();

        return result.isEmpty();
    }
}