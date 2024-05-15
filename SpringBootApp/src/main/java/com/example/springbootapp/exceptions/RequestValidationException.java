package com.example.springbootapp.exceptions;

public class RequestValidationException extends RuntimeException{
    private final String message;

    public RequestValidationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
