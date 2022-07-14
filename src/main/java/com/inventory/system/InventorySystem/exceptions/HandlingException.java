package com.inventory.system.InventorySystem.exceptions;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/* Exception handling */
@RestControllerAdvice
public class HandlingException {

    static Logger logger = LoggerFactory.getLogger(HandlingException.class);


    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> globalException(GlobalException ex) {
        String message = ex.message;
        int id = ex.id;
        return new ResponseEntity<>(new ApiResponse(message, id), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> exception = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            exception.put(field, defaultMessage);
        });
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> exception = new HashMap<>();
        String field = ex.getMethod();
        String message = ex.getMessage();
        exception.put(field, message);
        return new ResponseEntity<>(exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<?> methodArgumentTypeException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> exception = new HashMap<>();
        String field = "message";
        String message = ex.getMessage();
        exception.put(field, message);
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
