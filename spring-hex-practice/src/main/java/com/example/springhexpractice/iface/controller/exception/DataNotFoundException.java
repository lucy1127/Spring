package com.example.springhexpractice.iface.controller.exception;

public class DataNotFoundException extends RuntimeException{
    private final String errorMessage;

    public DataNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
