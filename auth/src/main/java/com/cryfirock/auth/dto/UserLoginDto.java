package com.cryfirock.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 1. DTO para login de usuario.
 * 2. Record:
 * - Es inmutable.
 * - Genera getters, toString, hashCode y equals.
 * - No requiere constructor ni setters.
 * - Validación más directa:
 * - @NotBlank: No puede ser nulo ni vacío.
 * - @Size: Define el rango de caracteres.
 * - Thread-safe no cambia y no te preocupa concurrencia ni estados raros.
 * 3. username: No puede ser nulo y debe tener entre 1 y 50 caracteres.
 * 4. password: No puede ser nulo y debe tener entre 6 y 128 caracteres.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public record UserLoginDto(
        @NotBlank @Size(min = 1, max = 50) String username,
        @NotBlank @Size(min = 6, max = 128) String password) {
}
