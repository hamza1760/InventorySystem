package com.inventory.system.InventorySystem.exceptions;

import com.inventory.system.InventorySystem.apiresponse.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.*;

import java.util.*;

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
