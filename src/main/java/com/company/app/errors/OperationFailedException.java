package com.company.app.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OperationFailedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OperationFailedException(String message) {
        super(message);
    }
}
