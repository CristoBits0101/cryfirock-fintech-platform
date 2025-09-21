package com.cryfirock.auth.service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cryfirock.auth.service.entities.User;

/**
 * ===============================================================================
 * Paso 4.1: La interfaz incluye consultas CRUD predefinidas
 * ===============================================================================
 */

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * ===========================================================================
     * Paso 4.2: Spring Data JPA crea la consulta según el nombre del método
     * ===========================================================================
     */

    // Estos existsBy están vinculados a anotaciones de validación personalizada
    // Las anotaciones se aplican en la clase User sobre sus atributos
    // Se consultan en BD para comprobar que no exista el valor antes de guardarlo
    // Hibernate la ejecuta y mapea a una instancia de la clase entidad
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    // Hibernate la ejecuta y mapea a una instancia de la clase entidad
    Optional<User> findByUsername(String username);

    // Usando JPQL (Java Persistence Query Language)
    // Consulta sobre la entidad User
    // Hibernate ve que User está mapeada a la tabla users
    // Hibernate traduce el JPQL a SQL real
    // Ese SQL se ejecuta en la base de datos
    // Hibernate la ejecuta y mapea a una instancia de la clase entidad
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> buscarPorEmail(@Param("email") String email);

    // Consulta nativa SQL puro sobre la tabla users
    // Hibernate la ejecuta y mapea a una instancia de la clase entidad
    @Query(value = "SELECT * FROM users WHERE active = true", nativeQuery = true)
    List<User> findAllActiveUsers();

}
