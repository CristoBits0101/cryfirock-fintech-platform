package com.cryfirock.auth.helper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cryfirock.auth.entity.Role;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.repository.JpaRoleRepository;

/**
 * 1. Pruebas unitarias para la clase RolesHelper.
 * 2. Verifica el correcto funcionamiento de la asignación de roles.
 * 3. Utiliza JUnit 5 y Mockito para las pruebas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@ExtendWith(MockitoExtension.class) @SuppressWarnings("unused")
class RolesHelperTest {
    @Mock
    private JpaRoleRepository roleRepository;

    @InjectMocks
    private RolesHelper rolesHelper;

    private Role roleUser;
    private Role roleAdmin;

    @BeforeEach
    void setUp() {
        roleUser = new Role("ROLE_USER");
        roleAdmin = new Role("ROLE_ADMIN");
    }

    @Nested @DisplayName("Tests para assignRoles")
    class AssignRolesTests {

        @Test @DisplayName("Debe asignar solo ROLE_USER a un usuario no administrador")
        void shouldAssignOnlyUserRoleToNonAdmin() {
            // Arrange.
            User user = new User();
            user.setAdmin(false);
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));

            // Act.
            List<Role> roles = rolesHelper.assignRoles(user);

            // Assert.
            assertEquals(1, roles.size());
            assertEquals("ROLE_USER", roles.get(0).getName());
            verify(roleRepository, times(1)).findByName("ROLE_USER");
            verify(roleRepository, never()).findByName("ROLE_ADMIN");
        }

        @Test @DisplayName("Debe asignar ROLE_USER y ROLE_ADMIN a un usuario administrador")
        void shouldAssignUserAndAdminRolesToAdmin() {
            // Arrange.
            User user = new User();
            user.setAdmin(true);
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
            when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(roleAdmin));

            // Act.
            List<Role> roles = rolesHelper.assignRoles(user);

            // Assert.
            assertEquals(2, roles.size());
            assertTrue(roles.stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
            assertTrue(roles.stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")));
        }

        @Test @DisplayName("Debe lanzar excepción si ROLE_USER no existe")
        void shouldThrowExceptionWhenRoleUserNotFound() {
            // Arrange.
            User user = new User();
            user.setAdmin(false);
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

            // Act & Assert.
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> rolesHelper.assignRoles(user));
            assertEquals("Missing role ROLE_USER", exception.getMessage());
        }

        @Test @DisplayName("Debe lanzar excepción si ROLE_ADMIN no existe para usuario admin")
        void shouldThrowExceptionWhenRoleAdminNotFoundForAdmin() {
            // Arrange.
            User user = new User();
            user.setAdmin(true);
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
            when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.empty());

            // Act & Assert.
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> rolesHelper.assignRoles(user));
            assertEquals("Missing role ROLE_ADMIN", exception.getMessage());
        }

        @Test @DisplayName("Debe retornar una lista modificable")
        void shouldReturnModifiableList() {
            // Arrange.
            User user = new User();
            user.setAdmin(false);
            when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));

            // Act.
            List<Role> roles = rolesHelper.assignRoles(user);

            // Assert - Verificar que la lista es modificable.
            assertDoesNotThrow(() -> roles.add(roleAdmin));
            assertEquals(2, roles.size());
        }
    }
}
