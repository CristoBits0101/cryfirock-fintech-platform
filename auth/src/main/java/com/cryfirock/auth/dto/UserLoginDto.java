package com.cryfirock.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDto(
    @NotBlank @Size(min = 1, max = 50) String username,
    @NotBlank @Size(min = 6, max = 128) String password) {}
