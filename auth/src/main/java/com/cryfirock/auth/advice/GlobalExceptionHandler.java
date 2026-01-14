package com.cryfirock.auth.advice;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.cryfirock.auth.exception.UserNotFoundException;
import com.cryfirock.auth.mapper.ErrorMapper;
import com.cryfirock.auth.model.ErrorResponse;

/**
 * 1. Controlador de manejo global de excepciones.
 * 2. Proporciona respuestas consistentes para diferentes tipos de excepciones.
 * 3. ErrorMapper para mapear excepciones a objetos de error personalizados.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 1. Fuente de mensajes para la internacionalización.
     * 2. Mapper para convertir excepciones en respuestas de error.
     */
    private final MessageSource messageSource;
    private final ErrorMapper errorMapper;

    /**
     * 1. Constructor para inyectar dependencias.
     *
     * @param messageSource Fuente de mensajes para la internacionalización.
     * @param errorMapper Mapper para convertir excepciones en respuestas error.
     */
    public GlobalExceptionHandler(MessageSource messageSource, ErrorMapper errorMapper) {
        this.messageSource = messageSource;
        this.errorMapper = errorMapper;
    }

    /**
     * 1. Manejo de excepciones para rutas no encontradas.
     *
     * @param e Excepción lanzada cuando no se encuentra una ruta.
     * @return Respuesta HTTP con estado 404 y detalles del error.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundEx(NoHandlerFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorMapper.toError(
                        HttpStatus.NOT_FOUND.value(),
                        "La ruta de la API REST no existe.",
                        e.getMessage()));
    }

    /**
     * 1. Manejo de errores de validación de argumentos.
     * 2. Devuelve un mapa de campos con errores y sus mensajes correspondientes.
     *
     * @param ex Excepción lanzada durante la validación de argumentos.
     * @return Mapa de campos con errores y mensajes.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        this::resolveMessage,
                        (existing, replacement) -> existing));
    }

    /**
     * 1. Manejo de excepciones para mensajes HTTP no legibles.
     * 2. Devuelve una respuesta con estado 400 y detalles del error.
     *
     * @param ex Excepción lanzada cuando el mensaje HTTP no es legible.
     * @return Respuesta HTTP con estado 400 y detalles del error.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableMessage(
            HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMapper.toError(
                        HttpStatus.BAD_REQUEST.value(),
                        "Malformed JSON request.",
                        "Request body is invalid or malformed."));
    }

    /**
     * 1. Manejo de excepciones internas del servidor.
     * 2. Devuelve un mapa con detalles del error.
     *
     * @param ex Excepción lanzada durante el procesamiento interno.
     * @return Mapa con detalles del error.
     */
    @ExceptionHandler({ NullPointerException.class,
            HttpMessageNotWritableException.class }) @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleInternalServerError(Exception ex) {
        return Map.of(
                "date", new Date(),
                "error", "El usuario o rol no existe!",
                "message", ex.getMessage(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * 1. Manejo de excepciones cuando un usuario no es encontrado.
     * 2. Devuelve una respuesta con estado 404 y detalles del error.
     *
     * @param ex Excepción lanzada cuando un usuario no es encontrado.
     * @return Respuesta HTTP con estado 404 y detalles del error.
     */
    @ExceptionHandler(UserNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorMapper.toError(
                        HttpStatus.NOT_FOUND.value(),
                        "Recurso no encontrado",
                        ex.getMessage()));
    }

    /**
     * 1. Resuelve el mensaje de error para un campo específico.
     *
     * @param error Error de campo a resolver.
     * @return Mensaje de error resuelto.
     */
    private String resolveMessage(FieldError error) {
        return (error == null)
                ? ""
                : messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
