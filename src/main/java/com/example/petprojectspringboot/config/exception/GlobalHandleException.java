package com.example.petprojectspringboot.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalHandleException {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomException> handleCustomException(CustomException e) {
        return ResponseEntity.status(408).body(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleCommonException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
