package com.example.springhexpractice.controller.error;

public class StopNotFoundException extends RuntimeException{
    private final String errorMessage;

    public StopNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
