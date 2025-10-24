package com.creativadigital360.api.core.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.creativadigital360.api.core.entity.Role;

/**
 * =========================================================================
 * Paso 5.1: La interfaz incluye consultas CRUD predefinidas
 * =========================================================================
 */
// Interface con métodos CRUD predefinidos de JPA ejecutados por Hibernate
public interface JpaRoleRepository extends CrudRepository<Role, Long> {

    /**
     * =====================================================================
     * Paso 5.2: Spring Data JPA crea la consulta según el nombre del método
     * =====================================================================
     */
    // Hibernate la ejecuta y mapea a una instancia de la clase entidad
    Optional<Role> findByName(String name);

}
