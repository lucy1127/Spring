package com.example.springhexpractice.controller.error;

public class TrainNotFoundException extends RuntimeException{
    private final String errorMessage;

    public TrainNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
