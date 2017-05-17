package com.possessor.exception.ErroMessage;

/**
 * Created by rpiotrowicz on 2017-05-16.
 */
public class ErrorMessage {

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage(String message){
        this.errorMessage = message;
    }
}
