package com.inventory.system.InventorySystem.exceptions;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/* Exception handling */
@RestControllerAdvice
public class HandlingException {

    static Logger logger = LoggerFactory.getLogger(HandlingException.class);


    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<?> alreadyExistsException(AlreadyExists ex) {
        AlreadyExistsConstant message = ex.message;
        int id = ex.id;
        return new ResponseEntity<>(new ApiResponse(message.getValue(), id), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        NotFoundConstant message = ex.message;
        int id = ex.id;
        return new ResponseEntity<>(new ApiResponse(message.getValue(), id), HttpStatus.NOT_FOUND);
    }
}
