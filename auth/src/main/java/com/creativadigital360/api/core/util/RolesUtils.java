package com.creativadigital360.api.core.util;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.creativadigital360.api.core.entity.Role;
import com.creativadigital360.api.core.entity.User;
import com.creativadigital360.api.core.repository.JpaRoleRepository;

import lombok.Getter;
import lombok.Setter;

/**
 * =======================================================================================
 * Paso 10.1: Resuelve y valida los roles canónicos de un usuario antes de persistirlo
 * =======================================================================================
 */
@Component
@Setter
@Getter
public class RolesUtils {

    /**
     * =======================================================================================
     * Paso 10.2: Atributos
     * =======================================================================================
     */
    // Roles canónicos para usuarios static para mantener una única copia por clase
    // Referencia final no cambia y bean scope singleton misma instancia en petición
    // Varios beans singleton de la misma clase comparten la misma variable estática
    // Los miembros estáticos se usan sin instanciar importando la clase
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    // Repositorios de acceso a datos final
    // Referencia final no cambia y bean scope singleton misma instancia en petición
    private final JpaRoleRepository roleRepository;

    /**
     * =======================================================================================
     * Paso 10.3: Constructores
     * =======================================================================================
     */
    // Inicializa el atributo al instanciar la clase
    public RolesUtils(JpaRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Calcula qué roles debe tener el usuario antes de guardarlo
    public List<Role> assignRoles(User user) {
        // Si es admin devuelve ROLE_USER y ROLE_ADMIN si no solo ROLE_USER
        return (user.isAdmin()
                // Si es admin devuelve Stream de ROLE_USER y ROLE_ADMIN
                ? Stream.of(ROLE_USER, ROLE_ADMIN)
                // Si no devuelve solo una Stream ROLE_USER
                : Stream.of(ROLE_USER))
                // Se ejecuta una vez por cada elemento del Stream
                .map(role -> roleRepository
                        // Busca el rol en la BD
                        .findByName(role)
                        // Lanza error si no existe
                        .orElseThrow(() -> new IllegalStateException("Missing role " + role)))
                // Ejecuta el Stream y crea la lista
                .toList();
    }

}
