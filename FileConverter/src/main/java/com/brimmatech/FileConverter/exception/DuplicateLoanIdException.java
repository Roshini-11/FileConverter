package com.brimmatech.FileConverter.exception;

public class DuplicateLoanIdException extends RuntimeException {

    public DuplicateLoanIdException(String message) {
        super(message);
    }

    public DuplicateLoanIdException(String message, Throwable cause) {
        super(message, cause);
    }
}

