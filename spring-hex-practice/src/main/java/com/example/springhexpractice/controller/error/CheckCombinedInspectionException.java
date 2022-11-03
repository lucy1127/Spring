package com.example.springhexpractice.controller.error;

import java.util.List;
import java.util.Map;

public class CheckCombinedInspectionException extends RuntimeException {
    private String error;
    private List<Map<String, String>> checkErrors;

    public CheckCombinedInspectionException(String error, List<Map<String, String>> m) {
        this.error = error;
        this.checkErrors = m;
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
