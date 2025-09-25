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

    // Create
    User save(User user);

    // Read
    List<User> findAll();

    Optional<User> findById(Long id);

    // Update
    Optional<User> update(Long id, User user);

    // Delete
    void deleteById(Long id);

    Optional<User> deleteUser(User user);

}
