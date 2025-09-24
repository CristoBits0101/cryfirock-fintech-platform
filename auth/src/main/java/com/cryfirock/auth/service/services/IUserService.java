package com.cryfirock.auth.service.services;

import java.util.List;
import java.util.Optional;

import com.cryfirock.auth.service.entities.User;

/**
 * ==============================================================
 * Paso 7.1: Interfaz de servicio para la gestión de usuarios
 * ==============================================================
 */

// Define las operaciones disponibles para la gestión de usuarios
public interface IUserService {

    /**
     * ==========================================================
     * Paso 7.2: Métodos de validación
     * ==========================================================
     */

    // Validación de existencia de atributos de usuario
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    /**
     * ==========================================================
     * Paso 7.3: Métodos CRUD
     * ==========================================================
     */

    // Operaciones CRUD de la entidad User
    List<User> findAll();

    Optional<User> deleteUser(User user);

    Optional<User> findById(Long id);

    Optional<User> update(Long id, User user);

    User save(User user);
}
