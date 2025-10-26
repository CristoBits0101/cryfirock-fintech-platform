package com.cryfirock.auth.advice;

import java.util.Date;
import java.util.HashMap;
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
import com.cryfirock.auth.model.Error;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private final MessageSource messageSource;
  private final ErrorMapper errorMapper;

  public GlobalExceptionHandler(MessageSource messageSource, ErrorMapper errorMapper) {
    this.messageSource = messageSource;
    this.errorMapper = errorMapper;
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e) {
    Error error = errorMapper.toError(
        HttpStatus.NOT_FOUND.value(),
        "La ruta de la API REST no existe.",
        e.getMessage());
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND.value())
        .body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
    return ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            this::resolveMessage,
            (existing, replacement) -> existing));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Error> handleUnreadableMessage(HttpMessageNotReadableException ex) {
    Error error = errorMapper.toError(
        HttpStatus.BAD_REQUEST.value(),
        "Malformed JSON request.",
        "Request body is invalid or malformed.");
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(error);
  }

  @ExceptionHandler({ NullPointerException.class, HttpMessageNotWritableException.class })
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
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(errorMapper.toError(
            HttpStatus.NOT_FOUND.value(),
            "Recurso no encontrado",
            ex.getMessage()));
  }

  private String resolveMessage(FieldError error) {
    return messageSource.getMessage(error, LocaleContextHolder.getLocale());
  }
}
