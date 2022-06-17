package com.oze.hospitalmanager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oze.hospitalmanager.models.Response;

@RestControllerAdvice(annotations = RestController.class, basePackages = "com.oze.hospitalmanager")
public class AppControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppControllerAdvice.class);

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException ex) {
        LOGGER.error("HttpMessageNotReadable Exception -> {} --- {}",
                ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new Response<>(ex.getMessage()));
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        LOGGER.error("MethodArgumentNotValidException Exception -> {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new Response<>(ex.getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception ex) {
        LOGGER.error("Generic Exception -> {}", ex.getMessage());
        return ResponseEntity.internalServerError()
                .body(new Response<>("An error occurred"));
    }

}
