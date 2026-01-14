package com.cryfirock.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cryfirock.auth.entity.Role;

/**
 * 1. Repositorio JPA para entidades Role.
 * 2. Anotado con @Repository para la detección automática por Spring.
 * 3. Proporciona métodos para operaciones CRUD y consultas personalizadas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Repository
public interface JpaRoleRepository extends CrudRepository<Role, Long> {
  /**
   * Busca un rol por su nombre.
   * 
   * @param name El nombre del rol a buscar.
   * @return Un Optional que contiene el rol si se encuentra o vacío si no.
   */
  Optional<Role> findByName(String name);
}
