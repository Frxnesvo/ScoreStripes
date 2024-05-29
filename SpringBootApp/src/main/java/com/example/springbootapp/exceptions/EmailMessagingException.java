package com.example.springbootapp.exceptions;

public class EmailMessagingException extends RuntimeException{

    private final String message;

    public EmailMessagingException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
