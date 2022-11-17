package com.example.springhexpractice.iface.controller.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckError {
    private String error;
    private List<Map<String, String>> checkErrors;


    public CheckError(CheckErrorException e){
        this.error = "VALIDATE FAILED";
        this.checkErrors = new ArrayList<>();

        e.getCheckError().forEach(error -> {
            Map<String, String> errMap = new HashMap<>();
            errMap.put("code", error.getCode());
            errMap.put("message", error.getMessage());
            this.checkErrors.add(errMap);
        } );
    }

    public List<Map<String, String>> getCheckErrors() {
        return checkErrors;
    }

    public void setCheckErrors(List<Map<String, String>> checkErrors) {
        this.checkErrors = checkErrors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
