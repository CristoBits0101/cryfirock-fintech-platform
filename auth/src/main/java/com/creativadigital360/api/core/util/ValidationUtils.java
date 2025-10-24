package com.creativadigital360.api.core.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * ===========================================================================================
 * Paso 14.1: Valida solicitud HTTP y devuelve respuesta de error en JSON
 * ===========================================================================================
 */
@Component
public class ValidationUtils {

    public ResponseEntity<?> reportIncorrectFields(BindingResult result) {
        // Crea un mapa para almacenar los errores de validación y sus mensajes
        Map<String, String> requestErrors = new HashMap<>();

        // Recorre cada campo con errores de validación y los agrega al mapa
        result.getFieldErrors().forEach(err -> {
            requestErrors.put(err.getField(), err.getDefaultMessage());
        });

        // Devuelve los errores de validación con código de estado 400
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(requestErrors);
    }

}
