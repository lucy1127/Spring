package com.example.springhexpractice.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorExceptionHandler {

    // 捕捉 MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handler(MethodArgumentNotValidException e) {
        ErrorResponse error = new ErrorResponse(e);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 捕捉 ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handler(ConstraintViolationException e) {

        ErrorResponse error = new ErrorResponse(e);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TrainNotFoundException.class)
    public ResponseEntity<ErrorMessage> handler(TrainNotFoundException e) {
        ErrorMessage error = new ErrorMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handler(NumberFormatException e) {
        ErrorResponse error = new ErrorResponse(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StopNotFoundException.class)
    public ResponseEntity<ErrorMessage> handler(StopNotFoundException e) {
        ErrorMessage error = new ErrorMessage(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CheckTrainException.class)
    public ResponseEntity<CheckError> handler(CheckTrainException e) {
        CheckError error = new CheckError(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckCombinedInspectionException.class)
    public ResponseEntity<CheckError> handler(CheckCombinedInspectionException e) {
        CheckError error = new CheckError(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
