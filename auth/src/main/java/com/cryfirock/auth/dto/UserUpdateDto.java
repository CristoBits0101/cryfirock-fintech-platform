package com.cryfirock.auth.dto;

import java.time.LocalDate;

import com.cryfirock.auth.model.AccountStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * 1. DTO para actualizar un usuario.
 * 2. Record:
 *      - Es inmutable.
 *      - Genera getters, toString, hashCode y equals.
 *      - No requiere constructor ni setters.
 *      - Validación más directa:
 *          - @Size: Define el rango de caracteres.
 *          - @Email: Define el formato de correo electrónico.
 *      - Thread-safe no cambia y no te preocupa concurrencia ni estados raros.
 * 3. givenName: Nombre dado.
 * 4. familyName: Apellido familiar.
 * 5. dob: Fecha de nacimiento.
 * 6. email: Correo electrónico.
 * 7. phoneNumber: Número de teléfono.
 * 8. address: Dirección.
 * 9. username: Nombre de usuario.
 * 10. passwordHash: Hash de la contraseña.
 * 11. admin: Indica si es administrador.
 * 12. enabled: Indica si el usuario está habilitado.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public record UserUpdateDto(
                @Size(max = 50) String givenName,
                @Size(max = 50) String familyName,
                LocalDate dob,
                @Email @Size(max = 100) String email,
                @Size(max = 20) String phoneNumber,
                @Size(max = 255) String address,
                @Size(min = 1, max = 50) String username,
                String passwordHash,
                Boolean admin,
                AccountStatus enabled) {
}
