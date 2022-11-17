package com.example.springhexpractice.iface.controller.exception;


public class ErrorMessage {

    private String error;

    public ErrorMessage (DataNotFoundException e){
        this.error = e.getErrorMessage();
    }


    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
