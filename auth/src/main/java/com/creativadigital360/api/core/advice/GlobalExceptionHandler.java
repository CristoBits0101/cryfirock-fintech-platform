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

/**
 * ============================================================================
 * Paso 15.1: Clase que gestiona las excepciones de la aplicación
 * ============================================================================
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ========================================================================
     * Paso 15.2: Atributos
     * ========================================================================
     */
    private final MessageSource messageSource;

    /**
     * ========================================================================
     * Paso 15.3: Constructores
     * ========================================================================
     */
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * ========================================================================
     * Paso 15.4: Método para manejar rutas no encontradas (404)
     * ========================================================================
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e) {
        // Instancia del modelo de Error
        Error error = new Error();
        // Inicializa los atributos de la clase Error
        error.setDate(new Date());
        error.setError("La ruta de la API REST no existe.");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        // Devuelve un error 404 personalizado
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    /**
     * ========================================================================
     * Paso 15.5: Método para manejar errores de validación
     * ========================================================================
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex
                .getBindingResult()
                .getFieldErrors()
                // Resuelve cada mensaje con el MessageSource y el locale actual
                .forEach(error -> errors.put(error.getField(), resolveMessage(error)));

        return errors;
    }

    /**
     * ========================================================================
     * Paso 15.6: Método para manejar JSON mal formado
     * ========================================================================
     */
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

    /**
     * ========================================================================
     * Paso 15.7: Método para manejar errores internos del servidor
     * ========================================================================
     */
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

    /**
     * ========================================================================
     * Paso 15.8: Método para manejar usuario no encontrado
     * ========================================================================
     */
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

    /**
     * ========================================================================
     * Paso 15.9: Método auxiliar para internacionalizar mensajes
     * ========================================================================
     */
    private String resolveMessage(FieldError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

}
