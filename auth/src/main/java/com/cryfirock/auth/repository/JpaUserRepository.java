package com.cryfirock.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cryfirock.auth.entity.User;

/**
 * 1. Repositorio JPA para entidades User.
 * 2. Anotado con @Repository para la detección automática por Spring.
 * 3. Proporciona métodos para operaciones CRUD y consultas personalizadas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    /**
     * Verifica si un usuario existe por su correo electrónico.
     *
     * @param email El correo electrónico a verificar.
     * @return true si el usuario existe y false en caso contrario.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si un usuario existe por su número de teléfono.
     *
     * @param phoneNumber El número de teléfono a verificar.
     * @return true si el usuario existe y false en caso contrario.
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * Verifica si un usuario existe por su nombre de usuario.
     *
     * @param username El nombre de usuario a verificar.
     * @return true si el usuario existe y false en caso contrario.
     */
    boolean existsByUsername(String username);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su correo electrónico.
     * Query personalizada usando JPQL.
     *
     * @param email El correo electrónico a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> buscarPorEmail(@Param("email") String email);

    /**
     * Busca un usuario por su ID con roles cargados.
     * Query personalizada usando JPQL.
     *
     * @param id ID del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findByIdWithRoles(@Param("id") Long id);

    /**
     * Encuentra todos los usuarios activos.
     * Query personalizada usando JPQL.
     *
     * @return Una lista de usuarios activos.
     */
    @Query(value = "SELECT * FROM users WHERE active = true", nativeQuery = true)
    List<User> findAllActiveUsers();
}
