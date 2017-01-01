package com.possesor.service;

import com.possesor.exception.Message;
import com.possesor.exception.ValidationError;
import com.possesor.model.User;
import com.possesor.repository.UserRepository;
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

        return userRepository.save(user).getId();
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    private void validateAdd(User user){
       List<IllegalArgumentException> exceptions = new LinkedList<>();

        if(user.getId() != null){
          exceptions.add(new IllegalArgumentException("id" + Message.NOT_ALLOWED));
        }

        if(user.getUsername() == null){
            exceptions.add(new IllegalArgumentException("username" + Message.MAY_NOT_BE_NULL));
        }

        if(user.getUsername() != null && user.getUsername().equals("")){
            exceptions.add(new IllegalArgumentException("username" + Message.MAY_NOT_BE_EMPTY));
        }

        if(user.getPassword() == null){
            exceptions.add(new IllegalArgumentException("password" + Message.MAY_NOT_BE_NULL));
        }

        if(user.getPassword() != null && user.getPassword().equals("")){
            exceptions.add(new IllegalArgumentException("password" + Message.MAY_NOT_BE_EMPTY));
        }

        if(user.getEmail() == null){
            exceptions.add(new IllegalArgumentException("email" + Message.MAY_NOT_BE_NULL));
        }

        if(user.getEmail() != null && user.getEmail().equals("")){
            exceptions.add(new IllegalArgumentException("email" + Message.MAY_NOT_BE_EMPTY));
        }

        if(!exceptions.isEmpty()){
            throw new ValidationError(exceptions);
        }
    }

    private void validForDataBase(User user){
        if(userRepository.findByUserNameAndPassword(user.getUsername(), user.getPassword()) != null){
            throw new IllegalArgumentException("User with username: " + user.getUsername() + " and password "
            + user.getPassword() + " " + Message.ALREADY_EXIST);
        }
    }
}
