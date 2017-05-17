package com.possessor.validator;

import com.possessor.dto.DtoUser;
import com.possessor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by rpiotrowicz on 2017-05-08.
 */

public class DtoUserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return DtoUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", HttpStatus.BAD_REQUEST.toString(),
                "Username could not be empty or has white space");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", HttpStatus.BAD_REQUEST.toString(),
                "Password could not be empty or has white space");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", HttpStatus.BAD_REQUEST.toString(),
                "Email could not be empty or has white space");

        DtoUser user = (DtoUser) target;

        if (!user.getEmail().contains("@")) {
            errors.reject(HttpStatus.BAD_REQUEST.toString(), "User email is not valid");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            errors.reject(HttpStatus.BAD_REQUEST.toString(), "There already exist user with email: " + user.getEmail());
        }
    }
}
