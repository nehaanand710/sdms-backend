package com.ase.project.sdms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
    super(message);
    }
}
