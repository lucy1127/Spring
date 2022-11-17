package com.example.springhexpractice.iface.controller.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private String error;
    private List<Map<String, String>> fieldError;

    public ErrorResponse(MethodArgumentNotValidException e) {

        this.fieldError = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(m -> {
            Map<String, String> fieldMap = new HashMap<>();

            // 欄位名稱
            fieldMap.put("files", m.getField());

            // 錯誤類型，例 : NotNull 或是 NotBlank
            fieldMap.put("code", m.getCode());

            // 錯誤訊息，例 : 年齡不可為空
            fieldMap.put("message", m.getDefaultMessage());

            fieldError.add(fieldMap);
        });
        this.error = "Validate Failed";
    }


    public ErrorResponse(ConstraintViolationException e) {

        this.fieldError = new ArrayList<>();
        e.getConstraintViolations().forEach(c -> {

            String fieldName = null;

            for (Path.Node node : c.getPropertyPath()) {
                fieldName = node.getName();
            }

            Map<String, String> map = new HashMap<>();
            // 錯誤類型，例 : NotNull 或是 NotBlank
            String code = c.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
//            map.put("code", c.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
            if (!"NotEmpty".equals(code))
                map.put("code", code);
            // 錯誤訊息，例 : 年齡不可為空
            map.put("message", c.getMessage());
            // 欄位名稱
            map.put("field", fieldName);


            fieldError.add(map);
        });
        this.error = "Validate Failed";

    }

    public ErrorResponse(NumberFormatException e) {

        this.fieldError = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("code", "Min");

        map.put("message", "車次必須為正整數");

        map.put("field", "train_no");

        fieldError.add(map);

        this.error = "Validate Failed";
    }

    // 處理 Exception
    public ErrorResponse(Exception e) {
        this.error = e.getMessage();
    }

    public List<Map<String, String>> getFieldError() {
        return fieldError;
    }

    public void setFieldError(List<Map<String, String>> fieldError) {
        this.fieldError = fieldError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
