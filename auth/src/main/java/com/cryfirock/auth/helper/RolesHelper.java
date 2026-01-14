package com.cryfirock.auth.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.cryfirock.auth.entity.Role;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.repository.JpaRoleRepository;

import lombok.Getter;
import lombok.Setter;

/**
 * 1. Clase auxiliar para asignar roles a usuarios.
 * 2. Proporciona métodos para determinar y asignar roles.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Component @Setter @Getter
public class RolesHelper {
    /**
     * 1. Roles predefinidos en el sistema.
     * 2. ROLE_USER: Rol básico para todos los usuarios.
     * 3. ROLE_ADMIN: Rol con privilegios administrativos.
     * 4. Se utilizan para controlar el acceso y las funcionalidades.
     * 5. Static para compartir atributos entre instancias.
     * 6. Final para evitar modificaciones en tiempo de ejecución.
     */
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final JpaRoleRepository roleRepository;

    /**
     * Constructor de la clase RolesHelper.
     *
     * @param roleRepository Repositorio de roles para acceder a los datos de roles.
     */
    public RolesHelper(JpaRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Asigna roles a un usuario basado en su estado administrativo.
     *
     * @param user Usuario al que se le asignarán los roles.
     * @return Lista de roles asignados al usuario.
     */
    public List<Role> assignRoles(User user) {
        /**
         * 1. Verifica si el usuario es administrador.
         * 2. Si es administrador se asigna ROLE_USER y ROLE_ADMIN.
         * 3. Si no es administrador se asigna solo ROLE_USER.
         * 4. Utiliza Streams para mapear los nombres de roles a objetos Role.
         * 5. Lanza una excepción si un rol no se encuentra en el repositorio.
         * 6. Devuelve una lista de roles asignados al usuario.
         * 7. Convierte el Stream resultante en una ArrayList.
         */
        return (user.isAdmin()
                ? Stream.of(ROLE_USER, ROLE_ADMIN)
                : Stream.of(ROLE_USER))
                        .map(role -> roleRepository
                                .findByName(role)
                                .orElseThrow(
                                        () -> new IllegalStateException("Missing role " + role)))
                        .collect(Collectors.toCollection(ArrayList::new));
    }
}
