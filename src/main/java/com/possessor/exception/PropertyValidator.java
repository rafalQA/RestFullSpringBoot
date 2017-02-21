package com.possessor.exception;

import com.possessor.model.ForeignCurrency;
import com.possessor.model.Property;
import com.possessor.repository.PropertyRepository;
import com.possessor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rpiotrowicz on 2017-02-17.
 */
@Component
public class PropertyValidator {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;


    public void validateAdd(Property property) {
        List<IllegalArgumentException> exceptions = new LinkedList<>();

        if (property.getPropertyId() != null) {
            exceptions.add(new IllegalArgumentException("id " + Message.NOT_ALLOWED));
        }

        if (property.getName() == null) {
            exceptions.add(new IllegalArgumentException("name " + Message.MAY_NOT_BE_NULL));
        }

        if (property.getName() != null && property.getName().equals("")) {
            exceptions.add(new IllegalArgumentException("name " + Message.MAY_NOT_BE_EMPTY));
        }

        if (property.getUser() != null) {
            exceptions.add(new IllegalArgumentException("user" + Message.NOT_ALLOWED));
        }

        if (property.getValue() == null) {
            exceptions.add(new IllegalArgumentException("value " + Message.MAY_NOT_BE_NULL));
        }

        if (property.getValue() != null && property.getValue().equals(BigDecimal.ZERO)) {
            exceptions.add(new IllegalArgumentException("value " + Message.MAY_NOT_BE_ZERO));
        }

        if (!exceptions.isEmpty()) {
            throw new ValidationException(exceptions);
        }
    }

    public void validateForDataBase(Property property) {
        if (propertyRepository.findByNameAndValueAndUser(
                property.getName(), property.getValue(), property.getUser()) != null) {
            throw new IllegalArgumentException("product " + Message.ALREADY_EXIST);
        }
    }

    public void userValidate(Long id) {
        if (userRepository.findOne(id) == null) {
            throw new IllegalArgumentException("user with id :" + id + "" + Message.NOT_FOUND);
        }
    }

    public void validForeignCurrency(String foreignCurrency) {
        boolean is = Arrays.stream(ForeignCurrency.values())
                .anyMatch(f -> f.name().equals(foreignCurrency));

        if (!is) {
            throw new IllegalArgumentException("allowed currency code is: " + Arrays.stream(ForeignCurrency.values())
                    .map(ForeignCurrency::name).collect(Collectors.joining("; ")));
        }
    }
}
