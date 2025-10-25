package com.cryfirock.auth.service;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * =====================================================================
 * Paso 7.1: Interfaz de servicio para la gestión de usuarios
 * =====================================================================
 */
// Define las operaciones disponibles para la gestión de usuarios
public interface IUserService {

    /**
     * =================================================================
     * Paso 7.2: Métodos CRUD
     * =================================================================
     */
    // Create
    User save(@NotNull User user);

    // Read
    List<User> findAll();

    Optional<User> findById(@NotNull Long id);

    // Update con entidad completa
    Optional<User> update(@NotNull Long id, @NotNull User user);

    // Update con DTO parcial
    Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto dto);

    // Delete devuelve opcional con el usuario eliminado
    Optional<User> deleteById(@NotNull Long id);

}
