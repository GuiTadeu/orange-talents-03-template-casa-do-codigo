package com.orange.bookstore.customer;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class PersonGroupSequenceProvider implements DefaultGroupSequenceProvider<CustomerForm> {

    @Override
    public List<Class<?>> getValidationGroups(CustomerForm customer) {
        List<Class<?>> groups = new ArrayList<>();

        if(hasPersonType(customer)) {
            if(customer.isPF()) {
                groups.add(PhysicalPersonGroup.class);
            } else if(customer.isPJ()) {
                groups.add(LegalPersonGroup.class);
            }
        }

        groups.add(CustomerForm.class);

        return groups;
    }

    private boolean hasPersonType(CustomerForm customer) {
        return nonNull(customer) && nonNull(customer.getPersonType());
    }
}
