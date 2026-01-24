package com.cryfirock.product.util;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 1. Utilidad para validaciones comunes.
 * 2. Proporciona métodos para validar cadenas y reportar errores de validación.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Component
public class ValidationUtil {
    /**
     * 1. Verifica si una cadena es válida.
     * 2. Una cadena es válida si no es nula y no está en blanco.
     *
     * @param value Cadena a validar.
     * @param exists Predicado para verificar existencia.
     * @return Verdadero si la cadena es válida.
     */
    public static boolean isValidString(String value, Predicate<String> exists) {
        return !isNullOrBlank(value) && exists != null && !exists.test(value.trim());
    }

    /**
     * 1. Verifica si una cadena es nula o está en blanco.
     * 2. Retorna verdadero si la cadena es nula o su contenido es solo espacios.
     *
     * @param value Cadena a validar.
     * @return Verdadero si la cadena es nula o está en blanco.
     */
    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    /**
     * 1. Busca el índice del primer valor no nulo ni vacío.
     * 2. Retorna 1 para el primer valor, 2 para el segundo, etc.
     * 3. Retorna Optional vacío si ningún valor es válido.
     *
     * @param values Cadenas a evaluar.
     * @return Optional con el índice (1-based) del primer valor válido.
     */
    public static Optional<Integer> findFirstNonBlankIndex(String... values) {
        return IntStream
                .range(0, values.length)
                .filter(i -> !isNullOrBlank(values[i]))
                .mapToObj(i -> i + 1)
                .findFirst();
    }

    /**
     * 1. Reporta los campos incorrectos en una respuesta HTTP.
     * 2. Utiliza el BindingResult para extraer errores de validación.
     * 3. Retorna un ResponseEntity con estado 400 y un mapa de errores.
     * 4. Si hay múltiples errores en el mismo campo, concatena los mensajes.
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
                                FieldError::getDefaultMessage,
                                (msg1, msg2) -> msg1 + "; " + msg2)));
    }
}
