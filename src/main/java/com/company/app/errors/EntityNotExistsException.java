package com.company.app.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotExistsException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EntityNotExistsException(String message) {
        super(message);
    }
}
