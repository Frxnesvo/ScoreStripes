package com.example.springbootapp.exceptions;

public class S3PutObjectException extends RuntimeException{
    private final String message;

    public S3PutObjectException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
