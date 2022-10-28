package com.example.Spring4.controller.error;

public class ErrorDataException  extends RuntimeException{
    private final String errorMessage;

    public ErrorDataException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
