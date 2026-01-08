package com.backend.Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//What it does: @RestControllerAdvice tells Spring:
// "I am a Super-Controller. I watch over ALL other controllers in the application."
@RestControllerAdvice
public class GlobalExceptionHandler {

    // This method catches validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex){
        // 1. Create a map to hold the errors (e.g., "email": "Invalid email format")
        Map<String,String> errors = new HashMap<>();

        // 2. Loop through all the errors Spring found
        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    String fieldName = error.getField();
                    String errMessage = error.getDefaultMessage();
                    errors.put(fieldName, errMessage);
                }
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}