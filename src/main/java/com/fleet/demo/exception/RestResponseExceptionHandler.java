package com.fleet.demo.exception;

import com.fleet.demo.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Error> handleValidation(ValidationException e) {
        return new ResponseEntity<>(Error.builder().error(e.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
    }
}
