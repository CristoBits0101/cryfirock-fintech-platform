package com.creativadigital360.auth.dto;

import java.time.LocalDate;

import com.creativadigital360.auth.model.AccountStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * =======================================================================
 * Paso 11.1: Dto record con los datos de actualización de usuario mínimos
 * =======================================================================
 */

// Menos código y más legible
// No puedes añadir estado mutable no hay setters
// Valores por defecto crear constructores adicionales
// equals/hashCode/toString listos sin Lombok
// Campos privados y final para cada componente
// Un constructor canónico con todos los componentes en orden
// Métodos de acceso getters sin prefijo get
// Clase y campos final no puede sustituir a una entidad
// Las entidades necesitan campos mutables
public record UserUpdateDto(

                /**
                 * =======================================================
                 * Paso 11.2: Atributos
                 * =======================================================
                 */

                // Identidad
                @Size(max = 50) String givenName,
                @Size(max = 50) String familyName,
                LocalDate dob,
                // Contacto
                @Email @Size(max = 100) String email,
                @Size(max = 20) String phoneNumber,
                @Size(max = 255) String address,
                // Cuenta
                @Size(min = 1, max = 50) String username,
                String passwordHash,
                // Acceso
                Boolean admin,
                AccountStatus enabled) {
}
