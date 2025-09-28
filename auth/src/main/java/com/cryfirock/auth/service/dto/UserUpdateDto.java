package com.cryfirock.auth.service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

// Dto con los datos de actualización de usuario mínimos
public record UserUpdateDto(
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
                Boolean enabled) {
}
