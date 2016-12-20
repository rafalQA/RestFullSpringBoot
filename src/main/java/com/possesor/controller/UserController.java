package com.possesor.controller;

import com.possesor.model.User;
import com.possesor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userRepository.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUser() {
        List<User> users = new LinkedList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}