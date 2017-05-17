package com.possessor.validator;

import com.possessor.model.Property;
import com.possessor.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

/**
 * Created by rpiotrowicz on 2017-05-15.
 */
public class PropertyValidator implements Validator {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Property.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", HttpStatus.BAD_REQUEST.toString(),
                "Name could not be empty or has white space");

        Property property = (Property) target;

        if(property.getPropertyId() != null){
            errors.reject(HttpStatus.BAD_REQUEST.toString(), "Property could not include propertyId");
        }

        if(property.getUser() != null){
            errors.reject(HttpStatus.BAD_REQUEST.toString(), "Property could not include user");
        }

        if(property.getValue().compareTo(BigDecimal.ZERO) != 1){
          errors.reject(HttpStatus.BAD_REQUEST.toString(), "Price could not be null or zero");
        }

        if(propertyRepository.existsByNameAndValueAndUser(property.getName(), property.getValue(), property.getUser())){
            errors.reject(HttpStatus.BAD_REQUEST.toString(), "Property already exist");
        }
    }
}
