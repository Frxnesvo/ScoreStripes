package com.example.springbootapp.exceptions;

public class OutOfStockException extends RuntimeException{
    private final String message;

    public OutOfStockException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
