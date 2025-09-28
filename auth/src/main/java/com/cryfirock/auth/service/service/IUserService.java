package com.cryfirock.auth.service.service;

import java.util.List;
import java.util.Optional;

import com.cryfirock.auth.service.dto.UserUpdateDto;
import com.cryfirock.auth.service.entity.User;

import jakarta.validation.constraints.NotNull;

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
    User save(@NotNull User user);

    // Read
    List<User> findAll();

    Optional<User> findById(@NotNull Long id);

    // Update
    Optional<User> update(Long id, User user);

    Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto dto);

    // Delete
    void deleteById(@NotNull Long id);

    Optional<User> deleteUser(@NotNull User user);

}
