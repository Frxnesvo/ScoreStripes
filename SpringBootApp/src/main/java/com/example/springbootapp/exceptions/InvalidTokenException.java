package com.example.springbootapp.exceptions;

public class InvalidTokenException extends RuntimeException{
    private final String message;

    public InvalidTokenException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {return message;}
}
