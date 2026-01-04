package com.cryfirock.oauth2.provider.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginRecord(
        @Size(min = 1, max = 50) String username,
        @Email @Size(max = 100) String email,
        @Size(max = 20) String phoneNumber) {
}