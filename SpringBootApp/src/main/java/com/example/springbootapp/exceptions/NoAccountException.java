package com.example.springbootapp.exceptions;

public class NoAccountException extends RuntimeException{
    private final String message;
    public NoAccountException(String message){
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
