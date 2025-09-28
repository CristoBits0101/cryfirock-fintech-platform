package com.cryfirock.auth.service.advice;

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

import com.cryfirock.auth.service.exception.UserNotFoundException;
import com.cryfirock.auth.service.model.Error;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestiona los errores 404
     *
     * @param e
     * @return respuesta personalizada
     * @throws NoHandlerFoundException
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e) {
        // Nueva instancia de Error
        Error error = new Error();

        // Inicializa los atributos
        error.setDate(new Date());
        error.setError("La ruta de la API REST no existe.");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());

        // Devuelve un error 404 personalizado
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    /**
     * Gestiona los errores de tipo null
     *
     * @param ex
     * @return mapa con la información del error
     * @throws Exception
     */
    @ExceptionHandler({
            NullPointerException.class,
            HttpMessageNotWritableException.class,
            UserNotFoundException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> userNotFoundException(Exception ex) {
        // Se devuelve un mapa asociativo y no el objeto en sí
        Map<String, Object> error = new HashMap<>();

        // Inicializa los atributos
        error.put("date", new Date());
        error.put("error", "el usuario o role no existe!");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }

}