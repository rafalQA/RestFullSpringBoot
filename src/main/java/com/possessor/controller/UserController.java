package com.possessor.controller;

import com.possessor.model.User;
import com.possessor.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "addUser", nickname = "addUser")
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @ApiOperation(value = "deleteUser", nickname = "deleteUser")
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @ApiOperation(value = "getAllUser", nickname = "getAllUser")
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
}