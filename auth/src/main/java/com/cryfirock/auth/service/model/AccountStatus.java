package com.cryfirock.auth.service.model;

// Estados permitidos en las cuentas de usuario
public enum AccountStatus {
    PENDING,
    ACTIVE,
    SUSPENDED,
    BANNED,
    CLOSED
}