package com.cryfirock.auth.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ====================================================================
 * Paso 18.1: DTO record con las credenciales mínimas de acceso a login
 * ====================================================================
 *
 * - Menos código y más legible
 * - Sin estado mutable (no setters)
 * - equals/hashCode/toString generados por el compilador
 * - Campos privados y finales para cada componente
 * - Getters sin prefijo (username(), password())
 * - No usar passwordHash aquí; el hash solo existe en la BD
 */
public record UserLoginDto(

                /**
                 * ====================================================
                 * Paso 18.2: Atributos con validaciones
                 * ====================================================
                 */

                @NotBlank @Size(min = 1, max = 50) String username,
                @NotBlank @Size(min = 6, max = 128) String password) {
}
