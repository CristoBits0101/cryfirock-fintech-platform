package com.cryfirock.auth.service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cryfirock.auth.service.entities.Role;

/**
 * =====================================================================
 * Paso 4.2: 
 * =======================================================================
 */

// Interface con métodos CRUD predefinidos de JPA ejecutados por Hibernate
public interface RoleRepository extends CrudRepository<Role, Long> {

    // Métodos personalizados del repositorio
    // Spring Data JPA crea la query por el nombre
    // Hibernate la ejecuta y mapea a una instancia de la clase entidad
    Optional<Role> findByName(String name);

}
