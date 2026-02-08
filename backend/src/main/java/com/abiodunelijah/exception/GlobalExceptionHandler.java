package com.abiodunelijah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest webRequest){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .statusCode("Internal Server Error")
                .message(ex.getMessage())
                .path(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException ex, WebRequest webRequest){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .statusCode("Not_Found_Exception")
                .message(ex.getMessage())
                .path(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
