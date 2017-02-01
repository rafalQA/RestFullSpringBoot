package com.possessor.exception;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rafal Piotrowicz on 01.01.2017.
 */
public class ValidationError extends RuntimeException {

    private List<IllegalArgumentException> illegalArgumentExceptions;

    public ValidationError(List<IllegalArgumentException> illegalArgumentExceptions) {
        super("Validation Errors: " + illegalArgumentExceptions.stream().map(IllegalArgumentException::getMessage)
                .collect(Collectors.joining(";")));

        this.illegalArgumentExceptions = illegalArgumentExceptions;
    }

    public List<IllegalArgumentException> getIllegalArgumentExceptions() {
        return illegalArgumentExceptions;
    }

    public void setIllegalArgumentExceptions(List<IllegalArgumentException> illegalArgumentExceptions) {
        this.illegalArgumentExceptions = illegalArgumentExceptions;
    }
}
