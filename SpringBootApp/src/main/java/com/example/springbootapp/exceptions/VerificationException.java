package com.example.springbootapp.exceptions;

public class VerificationException extends RuntimeException{
    private final String message;
    public VerificationException(String message){
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
