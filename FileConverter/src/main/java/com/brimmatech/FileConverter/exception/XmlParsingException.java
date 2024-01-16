package com.brimmatech.FileConverter.exception;

public class XmlParsingException extends RuntimeException {

    public XmlParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    private String errorMessage;

    public XmlParsingException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

