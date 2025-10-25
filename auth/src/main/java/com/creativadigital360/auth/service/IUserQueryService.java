package com.creativadigital360.auth.service;

/**
 * =============================================================
 * Paso 7.1: Interfaz de servicio para la validación de usuarios
 * =============================================================
 */
public interface IUserQueryService {

    /**
     * =========================================================
     * Paso 7.2: Métodos de validación
     * =========================================================
     */
    // Validación de existencia de atributos de usuario
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

}
