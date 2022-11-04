package com.example.springhexpractice.controller.error;


public class ErrorMessage {

    private String error;

    public ErrorMessage (NotFoundException e){
        this.error = e.getErrorMessage();
    }


    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
