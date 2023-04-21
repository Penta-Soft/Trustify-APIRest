package com.example.Trustifyrest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleAnyException(ResponseStatusException ex){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        if(ex.getStatusCode() == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.NOT_FOUND);
        else if(ex.getStatusCode() == HttpStatus.BAD_REQUEST)
            return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
