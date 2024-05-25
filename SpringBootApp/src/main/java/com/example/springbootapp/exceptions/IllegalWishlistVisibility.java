package com.example.springbootapp.exceptions;

public class IllegalWishlistVisibility extends RuntimeException{
    private final String message;

    public IllegalWishlistVisibility(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
