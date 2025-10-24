package com.creativadigital360.api.core.advice;

import com.creativadigital360.api.core.exception.UserNotFoundException;
import com.creativadigital360.api.core.model.Error;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e) {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("La ruta de la API REST no existe.");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex
                .getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), resolveMessage(error)));

        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleUnreadableMessage(HttpMessageNotReadableException ex) {
        Error error = new Error();

        error.setDate(new Date());
        error.setError("Malformed JSON request.");
        error.setMessage("Request body is invalid or malformed.");
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler({
            NullPointerException.class,
            HttpMessageNotWritableException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleInternalServerError(Exception ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("date", new Date());
        error.put("error", "el usuario o role no existe!");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleUserNotFound(UserNotFoundException ex) {
        Error error = new Error();

        error.setDate(new Date());
        error.setError("Recurso no encontrado");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    private String resolveMessage(FieldError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

}
