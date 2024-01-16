package com.brimmatech.FileConverter.exception;

public class DatabaseSaveException extends RuntimeException {

    private String message;

    public DatabaseSaveException(String message, Exception e) {
        super(e);
        this.message = message;
    }
}
