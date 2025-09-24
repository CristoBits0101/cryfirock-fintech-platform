package com.cryfirock.msvc.users.msvc_users.controllers;

import com.cryfirock.msvc.users.msvc_users.exceptions.UserNotFoundException;
import com.cryfirock.msvc.users.msvc_users.models.Error;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class HandlerExceptionController {

    /**
     * Handles 404 errors
     * 
     * @param e
     * @return ResponseEntity
     * @throws NoHandlerFoundException
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e) {
        // New error instance
        Error error = new Error();

        // Initialize the attributes
        error.setDate(new Date());
        error.setError("The REST API route does not exist.");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        // Returns a custom 404 error
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    /**
     * Handles null errors
     * 
     * @param ex
     * @return error
     * @throws Exception
     */
    @ExceptionHandler({
            NullPointerException.class,
            HttpMessageNotWritableException.class,
            UserNotFoundException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> userNotFoundException(Exception ex) {
        // We return an associative array and not the object itself
        Map<String, Object> error = new HashMap<>();

        // Initialize the attributes
        error.put("date", new Date());
        error.put("error", "el usuario o role no existe!");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }

}