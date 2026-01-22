package com.cryfirock.auth.service.api;

import java.util.List;
import java.util.Optional;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;

import jakarta.validation.constraints.NotNull;

/**
 * 1. Interfaz para el servicio de gestión de usuarios.
 * 2. Contrato de implementación para las clases que la implementen.
 * 3. Obliga a cumplir la convención de nombres y responsabilidades definidas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public interface IUserService {
    //============================================================================================
    // Métodos de creación
    //============================================================================================
    /**
     * 1. Guarda un usuario en el sistema.
     *
     * @param user el usuario a guardar.
     * @return el usuario guardado.
     */
    User save(@NotNull User user);

    //============================================================================================
    // Métodos de lectura
    //============================================================================================
    /**
     * 1. Obtiene todos los usuarios del sistema.
     *
     * @return lista de usuarios.
     */
    List<User> findAll();

    /**
     * 1. Busca un usuario por su ID.
     *
     * @param id el ID del usuario a buscar.
     * @return un Optional con el usuario si se encuentra, o vacío si no.
     */
    Optional<User> findById(@NotNull Long id);

    //============================================================================================
    // Métodos de actualización
    //============================================================================================
    /**
     * 1. Actualiza un usuario existente por su ID.
     *
     * @param id el ID del usuario a actualizar.
     * @param user el usuario con los datos actualizados.
     * @return un Optional con el usuario actualizado si se encuentra y o vacío si no.
     */
    Optional<User> update(@NotNull Long id, @NotNull User user);

    /**
     * 1. Actualiza un usuario existente por su ID usando un DTO.
     *
     * @param id el ID del usuario a actualizar.
     * @param dto el DTO con los datos actualizados.
     * @return un Optional con el usuario actualizado si se encuentra y o vacío si no.
     */
    Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto dto);

    //============================================================================================
    // Métodos de eliminación
    //============================================================================================
    /**
     * 1. Elimina un usuario por su ID.
     *
     * @param id el ID del usuario a eliminar.
     * @return un Optional con el usuario eliminado si se encuentra y o vacío si no.
     */
    Optional<User> deleteById(@NotNull Long id);
}
