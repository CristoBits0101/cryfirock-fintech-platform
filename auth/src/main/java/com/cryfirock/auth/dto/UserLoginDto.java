package com.cryfirock.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 1. DTO con menos campos para login de usuario.
 * 2. Record:
 *  - Es inmutable.
 *  - Genera getters.
 *  - Genera toString.
 *  - Genera hashCode.
 *  - Genera equals.
 *  - No requiere constructor ni setters.
 * 3. Validación más directa:
 *  - @NotBlank: No puede ser nulo ni vacío.
 *  - @Size: Define el rango de caracteres.
 *  - Thread-safe no cambia y no te preocupa concurrencia ni estados raros.
 * 4. username: No puede ser nulo y debe tener entre 1 y 50 caracteres.
 * 5. password: No puede ser nulo y debe tener entre 6 y 128 caracteres.
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
