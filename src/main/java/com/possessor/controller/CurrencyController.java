package com.possessor.controller;

import com.possessor.model.ForeignCurrency;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rpiotrowicz on 2017-06-14.
 */

@RestController
public class CurrencyController {

    @GetMapping(value = "/currency")
    @ResponseStatus(HttpStatus.OK)
    public List<ForeignCurrency> getCurrency(){
        return Arrays.asList(ForeignCurrency.values());
    }
}
