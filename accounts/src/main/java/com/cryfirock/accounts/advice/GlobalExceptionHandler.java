package com.cryfirock.accounts.advice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cryfirock.accounts.exception.AccountNotFoundException;

/**
 * 1. Manejador global de excepciones para el módulo de cuentas.
 * 2. Proporciona respuestas consistentes para errores comunes.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 1. Maneja excepciones de cuenta no encontrada.
     * 2. Retorna status 404 con el mensaje de error.
     *
     * @param ex Excepción capturada.
     * @return ResponseEntity con el error y status 404.
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFoundException(
            AccountNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * 1. Maneja excepciones de validación de argumentos.
     * 2. Retorna status 400 con los errores de validación.
     *
     * @param ex Excepción capturada.
     * @return ResponseEntity con los errores y status 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 1. Maneja excepciones generales no controladas.
     * 2. Retorna status 500 con mensaje genérico.
     *
     * @param ex Excepción capturada.
     * @return ResponseEntity con el error y status 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "Ha ocurrido un error inesperado");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
