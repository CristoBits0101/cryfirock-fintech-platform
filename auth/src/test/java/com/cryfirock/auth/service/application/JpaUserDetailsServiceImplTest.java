package com.cryfirock.auth.service.application;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cryfirock.auth.entity.Role;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.model.AccountStatus;
import com.cryfirock.auth.repository.JpaUserRepository;

/**
 * 1. Pruebas unitarias para JpaUserDetailsServiceImpl.
 * 2. Verifica el correcto funcionamiento del servicio de autenticación.
 * 3. Utiliza JUnit 5 y Mockito para las pruebas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@ExtendWith(MockitoExtension.class) @SuppressWarnings("unused")
class JpaUserDetailsServiceImplTest {
    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private JpaUserDetailsServiceImpl userDetailsService;

    private User testUser;
    private Role roleUser;
    private Role roleAdmin;

    @BeforeEach
    void setUp() {
        roleUser = new Role("ROLE_USER");
        roleAdmin = new Role("ROLE_ADMIN");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("juanperez");
        testUser.setPasswordHash("$2a$10$N9qo8uLOickgx2ZMRZoMy.Q3LjNBhGGkCcBv3Wv/NQVXUjKKJfWAW");
        testUser.setEnabled(AccountStatus.ACTIVE);
        testUser.setRoles(List.of(roleUser));
    }

    @Nested @DisplayName("Tests para loadUserByUsername")
    class LoadUserByUsernameTests {

        @Test @DisplayName("Debe cargar usuario existente")
        void shouldLoadExistingUser() {
            // Arrange
            when(userRepository.findByUsername("juanperez")).thenReturn(Optional.of(testUser));

            // Act
            UserDetails userDetails = userDetailsService.loadUserByUsername("juanperez");

            // Assert
            assertNotNull(userDetails);
            assertEquals("juanperez", userDetails.getUsername());
            assertTrue(userDetails.isEnabled());
            assertEquals(1, userDetails.getAuthorities().size());
        }

        @Test @DisplayName("Debe lanzar excepción si el usuario no existe")
        void shouldThrowExceptionWhenUserNotFound() {
            // Arrange
            when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

            // Act & Assert
            UsernameNotFoundException exception = assertThrows(
                    UsernameNotFoundException.class,
                    () -> userDetailsService.loadUserByUsername("noexiste"));
            assertTrue(exception.getMessage().contains("noexiste"));
        }

        @Test @DisplayName("Debe cargar las autoridades correctamente")
        void shouldLoadAuthoritiesCorrectly() {
            // Arrange
            testUser.setRoles(List.of(roleUser, roleAdmin));
            when(userRepository.findByUsername("juanperez")).thenReturn(Optional.of(testUser));

            // Act
            UserDetails userDetails = userDetailsService.loadUserByUsername("juanperez");

            // Assert
            assertEquals(2, userDetails.getAuthorities().size());
            assertTrue(userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
            assertTrue(userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        }

        @Test @DisplayName("Debe marcar como deshabilitado si la cuenta está suspendida")
        void shouldBeDisabledWhenAccountSuspended() {
            // Arrange
            testUser.setEnabled(AccountStatus.SUSPENDED);
            when(userRepository.findByUsername("juanperez")).thenReturn(Optional.of(testUser));

            // Act
            UserDetails userDetails = userDetailsService.loadUserByUsername("juanperez");

            // Assert
            assertFalse(userDetails.isEnabled());
        }

        @Test @DisplayName("Debe marcar como deshabilitado si la cuenta está baneada")
        void shouldBeDisabledWhenAccountBanned() {
            // Arrange
            testUser.setEnabled(AccountStatus.BANNED);
            when(userRepository.findByUsername("juanperez")).thenReturn(Optional.of(testUser));

            // Act
            UserDetails userDetails = userDetailsService.loadUserByUsername("juanperez");

            // Assert
            assertFalse(userDetails.isEnabled());
        }

        @Test @DisplayName("Debe marcar como deshabilitado si la cuenta está pendiente")
        void shouldBeDisabledWhenAccountPending() {
            // Arrange
            testUser.setEnabled(AccountStatus.PENDING);
            when(userRepository.findByUsername("juanperez")).thenReturn(Optional.of(testUser));

            // Act
            UserDetails userDetails = userDetailsService.loadUserByUsername("juanperez");

            // Assert
            assertFalse(userDetails.isEnabled());
        }

        @Test @DisplayName("Debe estar habilitado si la cuenta está activa")
        void shouldBeEnabledWhenAccountActive() {
            // Arrange
            testUser.setEnabled(AccountStatus.ACTIVE);
            when(userRepository.findByUsername("juanperez")).thenReturn(Optional.of(testUser));

            // Act
            UserDetails userDetails = userDetailsService.loadUserByUsername("juanperez");

            // Assert
            assertTrue(userDetails.isEnabled());
        }
    }
}
