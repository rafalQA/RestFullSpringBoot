package com.possessor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by rpiotrowicz on 2017-06-05.
 */

@RestController
public class UiApplication {

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public Principal user(Principal user){
        return user;
    }
}
