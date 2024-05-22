package com.example.springbootapp.exceptions;

public class RateLimitExceededException extends RuntimeException{
    private final String message;

    public RateLimitExceededException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
