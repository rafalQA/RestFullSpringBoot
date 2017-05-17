package com.possessor.controller;

import com.google.common.base.Throwables;
import com.possessor.exception.ErroMessage.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by rpiotrowicz on 2017-05-16.
 */

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage notFoundException(Exception exception, WebRequest request){
        return new ErrorMessage(Throwables.getRootCause(exception).getMessage());
    }
}

