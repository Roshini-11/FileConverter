package com.brimmatech.FileConverter.exception;

public class ConversionException extends RuntimeException {

    private String message;

    public ConversionException(String message, Exception e) {
        super(e);
        this.message = message;
    }
}

