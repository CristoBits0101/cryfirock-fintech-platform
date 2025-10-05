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

/**
 * ============================================================================
 * Paso 15.1: Clase que gestiona las excepciones de la aplicación
 * ============================================================================
 */

// Marcar clase como manejador global de excepciones para controladores REST
@RestControllerAdvice

// Si se quiere limitar a un paquete concreto
// @RestControllerAdvice(basePackages = "com.cryfirock.auth.controller")

// Si se quiere limitar a un controlador concreto
// @RestControllerAdvice(assignableTypes = {UserController.class})
public class GlobalExceptionHandler {

    /**
     * ========================================================================
     * Paso 14.2: Método para manejar excepciones específicas
     * ========================================================================
     */

    /**
     * Gestiona los errores 404: Ruta no encontrada
     *
     * @param e: Excepción lanzada
     * @return Respuesta personalizada
     * @throws NoHandlerFoundException
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException e) {
        // Instancia del modelo de Error
        Error error = new Error();

        // Inicializa los atributos de la clase Error
        // Usa la fecha actual
        error.setDate(new Date());
        // Mensaje de error personalizado
        error.setError("La ruta de la API REST no existe.");
        // Mensaje de la excepción
        error.setMessage(e.getMessage());
        // Código de estado HTTP 404
        error.setStatus(HttpStatus.NOT_FOUND.value());

        // Devuelve un error 404 personalizado
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    /**
     * ========================================================================
     * Paso 14.3: Método para manejar varias excepciones
     * ========================================================================
     */

    /**
     * Gestiona los errores
     *
     * @param ex: Excepción lanzada
     * @return Mapa con la información del error
     * @throws Exception
     */
    @ExceptionHandler({
            // Maneja varios tipos de excepciones
            NullPointerException.class,
            // Maneja errores al escribir mensajes HTTP
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