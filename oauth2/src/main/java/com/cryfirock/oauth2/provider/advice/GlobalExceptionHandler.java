package com.cryfirock.oauth2.provider.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cryfirock.oauth2.provider.model.Error;

import feign.FeignException;

/**
 * 1. Manejador global de excepciones para el módulo OAuth2.
 * 2. Captura y transforma las excepciones en respuestas HTTP apropiadas.
 * 3. Proporciona respuestas consistentes de error en formato JSON.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 1. Maneja excepciones cuando un usuario no es encontrado.
     * 2. Retorna una respuesta HTTP 404 con detalles del error.
     *
     * @param ex Excepción Feign de tipo NotFound.
     * @return ResponseEntity con el error formateado.
     */
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
