package com.ase.project.sdms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PasswordAndConfirmPasswordMismatchException extends RuntimeException {
    public PasswordAndConfirmPasswordMismatchException(String message) {
        super(message);
    }
}