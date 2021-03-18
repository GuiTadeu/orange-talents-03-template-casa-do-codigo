package com.orange.bookstore.share;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @PersistenceContext
    private EntityManager manager;

    private String fieldName;
    private Class<?> domainClass;

    @Override
    public void initialize(UniqueValue params) {
        fieldName = params.fieldName();
        domainClass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("SELECT 1 FROM " + domainClass.getName() + " WHERE " + fieldName + " = :value");
        query.setParameter("value", value);
        List<?> resultList = query.getResultList();

        Assert.state(resultList.size() <= 1, "Valor duplicado não permitido!");

        return resultList.isEmpty();
    }
}
