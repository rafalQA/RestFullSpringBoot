package com.possessor.exception;

/**
 * Created by Rafal Piotrowicz on 01.01.2017.
 */
public enum Message {
    MAY_NOT_BE_NULL("may not be null"),
    MAY_NOT_BE_EMPTY("may not be empty"),
    NOT_ALLOWED("not allowed"),
    MAY_NOT_BE_ZERO("may not equals zero"),
    ALREADY_EXIST("already exist"),
    NOT_FOUND("not found");

    private String message;

    Message(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
