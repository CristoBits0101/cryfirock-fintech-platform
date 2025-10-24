package com.creativadigital360.api.core.dto;

import com.creativadigital360.api.core.model.AccountStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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
        AccountStatus enabled) {}
