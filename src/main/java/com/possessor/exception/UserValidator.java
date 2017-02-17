package com.possessor.exception;

import com.possessor.model.User;
import com.possessor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rpiotrowicz on 2017-02-17.
 */
@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public void validateAdd(User user) {
        List<IllegalArgumentException> exceptions = new LinkedList<>();

        if (user.getUserId() != null) {
            exceptions.add(new IllegalArgumentException("id" + Message.NOT_ALLOWED));
        }

        if (user.getAccount().getUsername() == null) {
            exceptions.add(new IllegalArgumentException("username " + Message.MAY_NOT_BE_NULL));
        }

        if (user.getAccount().getUsername() != null && user.getAccount().getUsername().equals("")) {
            exceptions.add(new IllegalArgumentException("username " + Message.MAY_NOT_BE_EMPTY));
        }

        if (user.getAccount().getPassword() == null) {
            exceptions.add(new IllegalArgumentException("password " + Message.MAY_NOT_BE_NULL));
        }

        if (user.getAccount().getPassword() != null && user.getAccount().getPassword().equals("")) {
            exceptions.add(new IllegalArgumentException("password " + Message.MAY_NOT_BE_EMPTY));
        }

        if (user.getEmail() == null) {
            exceptions.add(new IllegalArgumentException("email" + Message.MAY_NOT_BE_NULL));
        }

        if (user.getEmail() != null && user.getEmail().equals("")) {
            exceptions.add(new IllegalArgumentException("email " + Message.MAY_NOT_BE_EMPTY));
        }

        if (!exceptions.isEmpty()) {
            throw new ValidationException(exceptions);
        }
    }

    public void validForDataBase(User user) {
        if (userRepository.findByAccountUsernameAndAccountPassword(user.getAccount().getUsername(),
                user.getAccount().getPassword()) != null) {
            throw new IllegalArgumentException("User with username: " + user.getAccount().getUsername() + " and password "
                    + user.getAccount().getPassword() + " " + Message.ALREADY_EXIST);
        }
    }

}
