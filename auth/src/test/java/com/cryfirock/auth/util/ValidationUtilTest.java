package com.cryfirock.auth.util;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 1. Pruebas unitarias para la clase ValidationUtil.
 * 2. Verifica el correcto funcionamiento de las validaciones.
 * 3. Utiliza JUnit 5 y Mockito para las pruebas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@SuppressWarnings("unused")
class ValidationUtilTest {
    @Nested @DisplayName("Tests para isValidString")
    class IsValidStringTests {

        @Test @DisplayName("Debe retornar false si la cadena es null")
        void shouldReturnFalseWhenNull() {
            assertFalse(ValidationUtil.isValidString(null, s -> false));
        }

        @Test @DisplayName("Debe retornar false si el predicado es null")
        void shouldReturnFalseWhenPredicateIsNull() {
            assertFalse(ValidationUtil.isValidString("valor", null));
        }

        @Test @DisplayName("Debe retornar false si el valor ya existe")
        void shouldReturnFalseWhenValueExists() {
            assertFalse(ValidationUtil.isValidString("existente", s -> true));
        }

        @Test @DisplayName("Debe retornar true si el valor es válido y no existe")
        void shouldReturnTrueWhenValidAndNotExists() {
            assertTrue(ValidationUtil.isValidString("nuevo", s -> false));
        }

        @Test @DisplayName("Debe trimear el valor antes de verificar existencia")
        void shouldTrimValueBeforeCheckingExistence() {
            // El predicado verifica si recibe "trimmed"
            assertTrue(ValidationUtil.isValidString("  trimmed  ", s -> s.equals("noexiste")));
        }
    }

    @Nested @DisplayName("Tests para reportIncorrectFields")
    class ReportIncorrectFieldsTests {

        @SuppressWarnings("null") @Test @DisplayName("Debe retornar BAD_REQUEST con errores de validación")
        void shouldReturnBadRequestWithValidationErrors() {
            // Arrange
            BindingResult bindingResult = mock(BindingResult.class);
            FieldError fieldError = new FieldError("user", "email", "Email inválido");
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

            // Act
            ResponseEntity<?> response = ValidationUtil.reportIncorrectFields(bindingResult);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            @SuppressWarnings("unchecked")
            Map<String, String> errors = (Map<String, String>) response.getBody();
            assertEquals("Email inválido", errors.get("email"));
        }

        @SuppressWarnings("null") @Test @DisplayName("Debe manejar múltiples errores en el mismo campo")
        void shouldHandleMultipleErrorsOnSameField() {
            // Arrange
            BindingResult bindingResult = mock(BindingResult.class);
            FieldError error1 = new FieldError("user", "password", "Muy corta");
            FieldError error2 = new FieldError("user", "password", "Sin mayúsculas");
            when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));

            // Act
            ResponseEntity<?> response = ValidationUtil.reportIncorrectFields(bindingResult);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            @SuppressWarnings("unchecked")
            Map<String, String> errors = (Map<String, String>) response.getBody();
            String passwordError = errors.get("password");
            assertTrue(passwordError.contains("Muy corta"));
            assertTrue(passwordError.contains("Sin mayúsculas"));
        }

        @SuppressWarnings("null") @Test @DisplayName("Debe retornar mapa vacío si no hay errores")
        void shouldReturnEmptyMapWhenNoErrors() {
            // Arrange
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of());

            // Act
            ResponseEntity<?> response = ValidationUtil.reportIncorrectFields(bindingResult);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            @SuppressWarnings("unchecked")
            Map<String, String> errors = (Map<String, String>) response.getBody();
            assertTrue(errors.isEmpty());
        }

        @SuppressWarnings("null") @Test @DisplayName("Debe manejar múltiples campos con errores")
        void shouldHandleMultipleFieldsWithErrors() {
            // Arrange
            BindingResult bindingResult = mock(BindingResult.class);
            FieldError emailError = new FieldError("user", "email", "Email inválido");
            FieldError usernameError = new FieldError("user", "username", "Username requerido");
            when(bindingResult.getFieldErrors()).thenReturn(List.of(emailError, usernameError));

            // Act
            ResponseEntity<?> response = ValidationUtil.reportIncorrectFields(bindingResult);

            // Assert
            @SuppressWarnings("unchecked")
            Map<String, String> errors = (Map<String, String>) response.getBody();
            assertEquals(2, errors.size());
            assertEquals("Email inválido", errors.get("email"));
            assertEquals("Username requerido", errors.get("username"));
        }
    }
}
