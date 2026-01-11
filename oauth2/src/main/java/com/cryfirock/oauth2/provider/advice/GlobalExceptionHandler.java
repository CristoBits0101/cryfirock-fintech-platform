package com.cryfirock.oauth2.provider.advice;

import java.util.Date;

import com.cryfirock.oauth2.provider.model.Error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleUserNotFound(FeignException.NotFound ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Error(
                        "El usuario solicitado no existe en el sistema",
                        "Usuario no encontrado",
                        HttpStatus.NOT_FOUND.value(),
                        new Date()));
    }
}
