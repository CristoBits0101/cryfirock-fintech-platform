package com.cryfirock.auth.service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

public record UserUpdateDto(
        @Size(max = 50) String givenName,
        @Size(max = 50) String familyName,
        LocalDate dob,

        @Email @Size(max = 100) String email,
        @Size(max = 20) String phoneNumber,
        @Size(max = 255) String address,

        @Size(min = 1, max = 50) String username,

        String passwordHash,

        Boolean enabled) {
}
