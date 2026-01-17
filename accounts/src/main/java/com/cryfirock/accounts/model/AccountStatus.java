package com.cryfirock.accounts.model;

/**
 * Enum que representa el estado actual de una cuenta.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AccountStatus {
    ACTIVE,     // Cuenta activa y operativa.
    SUSPENDED,  // Cuenta suspendida temporalmente por seguridad y cumplimiento o incidencias.
    CLOSED      // Cuenta cerrada y no operativa.
}
