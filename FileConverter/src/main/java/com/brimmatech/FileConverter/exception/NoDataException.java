package com.brimmatech.FileConverter.exception;

public class NoDataException extends RuntimeException{

    private String message;

    public NoDataException(String message, Exception e) {
        super(e);
        this.message = message;
    }
}
