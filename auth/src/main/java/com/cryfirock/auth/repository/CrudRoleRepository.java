package com.cryfirock.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cryfirock.auth.entity.Role;

/**
 * 1. Repositorio JPA para entidades Role.
 * 2. Anotado con @Repository para la detección automática por Spring.
 * 3. Proporciona métodos para operaciones CRUD y consultas personalizadas:
 * - save(): Guarda una entidad Role.
 * - saveAll(): Guarda una lista de entidades Role.
 * - findById(): Busca una entidad Role por su ID.
 * - findAll(): Busca todas las entidades Role.
 * - delete(): Elimina una entidad Role.
 * - deleteById(): Elimina una entidad Role por su ID.
 * - deleteAll(): Elimina todas las entidades Role.
 * - count(): Cuenta el número de entidades Role.
 * - existsById(): Verifica si una entidad Role existe por su ID.
 * - findByName(): Busca una entidad por su nombre.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Repository
public interface CrudRoleRepository extends CrudRepository<Role, Long> {
    // ============================================================================================
    // --- Métodos de Spring Data JPA ---
    // ============================================================================================
    /**
     * Busca un rol por su nombre.
     *
     * @param name El nombre del rol a buscar.
     * @return Un Optional que contiene el rol si se encuentra o vacío si no.
     */
    Optional<Role> findByName(String name);
}
