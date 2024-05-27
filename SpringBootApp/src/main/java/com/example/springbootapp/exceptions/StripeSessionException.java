package com.example.springbootapp.exceptions;

public class StripeSessionException extends RuntimeException{
    private final String message;

    public StripeSessionException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
