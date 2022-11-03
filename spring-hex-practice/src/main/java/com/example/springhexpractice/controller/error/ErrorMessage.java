package com.example.springhexpractice.controller.error;

import java.util.List;
import java.util.Map;

public class ErrorMessage {

    private String error;

    public ErrorMessage (TrainNotFoundException e){
        this.error = e.getErrorMessage();
    }

    public ErrorMessage (StopNotFoundException e){
        this.error = e.getErrorMessage();
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
