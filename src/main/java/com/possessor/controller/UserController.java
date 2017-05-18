package com.possessor.controller;

import com.possessor.dto.DtoUser;
import com.possessor.service.CredentialsMailSender;
import com.possessor.mapper.UserMapper;
import com.possessor.model.User;
import com.possessor.service.UserService;
import com.possessor.validator.DtoUserValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DtoUserValidator validator;
    @Autowired
    private CredentialsMailSender mailSender;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @ApiOperation(value = "add user", response = Long.class)
    @RequestMapping(method = RequestMethod.POST, value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addUser(@Valid @RequestBody DtoUser dtoUser) {
        User user = userMapper.userDtoToUser(dtoUser);
        long userId = userService.addUser(user);

        if (userId > 0) {
            mailSender.sendCredentials(dtoUser);
        }

        return userId;
    }

    @ApiOperation(value = "get all user", response = DtoUser.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseStatus(HttpStatus.OK)
    public List<DtoUser> getAllUser() {
        return userService.getAllUser().stream()
                .map(user -> userMapper.userToDtoUser(user))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "delete user")
    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}