package com.example.springhexpractice.controller.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckError {
    private String error;
    private List<Map<String, String>> checkErrors;

    public CheckError(CheckCombinedInspectionException e) {
        this.error = e.getError();
        this.checkErrors = e.getCheckErrors();
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
