package com.possessor.service;

import com.possessor.exception.Message;
import com.possessor.exception.ValidationException;
import com.possessor.model.User;
import com.possessor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rafal Piotrowicz on 31.12.2016.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Long addUser(User user) {
        validateAdd(user);
        validForDataBase(user);

        return userRepository.save(user).getUserId();
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    private void validateAdd(User user) {
        List<IllegalArgumentException> exceptions = new LinkedList<>();

        if (user.getUserId() != null) {
            exceptions.add(new IllegalArgumentException("id" + Message.NOT_ALLOWED));
        }

        if (user.getAccount().getName() == null) {
            exceptions.add(new IllegalArgumentException("username " + Message.MAY_NOT_BE_NULL));
        }

        if (user.getAccount().getName() != null && user.getAccount().getName().equals("")) {
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

    private void validForDataBase(User user) {
        if (userRepository.findByAccountNameAndAccountPassword(user.getAccount().getName(),
                user.getAccount().getPassword()) != null) {
            throw new IllegalArgumentException("User with username: " + user.getAccount().getName() + " and password "
                    + user.getAccount().getPassword() + " " + Message.ALREADY_EXIST);
        }
    }
}
