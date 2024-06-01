package com.example.springbootapp.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private final String message;
    public UserAlreadyExistsException(String message){
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
