package com.possessor.controller;

import com.possessor.dto.DtoUser;
import com.possessor.mapper.UserMapper;
import com.possessor.model.User;
import com.possessor.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "add user", response = Long.class)
    @RequestMapping(method = RequestMethod.PUT, value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addUser(@RequestBody DtoUser dtoUser) {
        User user = UserMapper.INSTANCE.userDtoToUser(dtoUser);

        return userService.addUser(user);
    }

    @ApiOperation(value = "get all user", response = DtoUser.class, responseContainer ="List")
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseStatus(HttpStatus.OK)
    public List<DtoUser> getAllUser() {
        return userService.getAllUser().stream()
                .map(UserMapper.INSTANCE::userToDtoUser)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "delete user")
    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}