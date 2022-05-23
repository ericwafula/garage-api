package com.victor.garageapi.error;

import com.victor.garageapi.error.exceptions.EntityAlreadyExistsException;
import com.victor.garageapi.error.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> entityAlreadyExistsException(EntityAlreadyExistsException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
