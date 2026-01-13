package com.cryfirock.auth.util;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 1. Utilidad para validaciones comunes.
 * 2. Proporciona métodos para validar cadenas y reportar errores de validación.
 */
@Component
public class ValidationUtil {
  /**
   * 1. Verifica si una cadena es válida.
   * 2. Una cadena es válida si no es nula y no está en blanco.
   * 
   * @param value  Cadena a validar.
   * @param exists Predicado para verificar existencia.
   * @return Verdadero si la cadena es válida.
   */
  public static boolean isValidString(String value, Predicate<String> exists) {
    return !isBlankOrNull(value) && exists != null && !exists.test(value.trim());
  }

  /**
   * 1. Verifica si una cadena es nula o está en blanco.
   * 2. Retorna verdadero si la cadena es nula o su contenido es solo espacios.
   * 
   * @param value Cadena a validar.
   * @return Verdadero si la cadena es nula o está en blanco.
   */
  public static boolean isBlankOrNull(String value) {
    return value == null || value.isBlank();
  }

  /**
   * 1. Reporta los campos incorrectos en una respuesta HTTP.
   * 2. Utiliza el BindingResult para extraer errores de validación.
   * 3. Retorna un ResponseEntity con estado 400 y un mapa de errores.
   * 
   * @param result Resultado de la validación.
   * @return ResponseEntity con los errores de validación.
   */
  public static ResponseEntity<?> reportIncorrectFields(BindingResult result) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(result
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage)));
  }
}
