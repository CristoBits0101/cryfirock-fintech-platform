package com.cryfirock.auth.service.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cryfirock.auth.repository.JpaUserRepository;

/**
 * 1. Pruebas unitarias para UserQueryServiceImpl.
 * 2. Verifica el correcto funcionamiento de las consultas de existencia.
 * 3. Utiliza JUnit 5 y Mockito para las pruebas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@ExtendWith(MockitoExtension.class) @SuppressWarnings("unused")
class UserQueryServiceImplTest {
    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private UserQueryServiceImpl userQueryService;

    @Nested @DisplayName("Tests para existsByEmail")
    class ExistsByEmailTests {

        @Test @DisplayName("Debe retornar true si el email existe")
        void shouldReturnTrueWhenEmailExists() {
            // Arrange.
            when(userRepository.existsByEmail("existe@test.com")).thenReturn(true);

            // Act.
            boolean result = userQueryService.existsByEmail("existe@test.com");

            // Assert.
            assertTrue(result);
        }

        @Test @DisplayName("Debe retornar false si el email no existe")
        void shouldReturnFalseWhenEmailNotExists() {
            // Arrange.
            when(userRepository.existsByEmail("noexiste@test.com")).thenReturn(false);

            // Act.
            boolean result = userQueryService.existsByEmail("noexiste@test.com");

            // Assert.
            assertFalse(result);
        }
    }

    @Nested @DisplayName("Tests para existsByPhoneNumber")
    class ExistsByPhoneNumberTests {

        @Test @DisplayName("Debe retornar true si el teléfono existe")
        void shouldReturnTrueWhenPhoneExists() {
            // Arrange.
            when(userRepository.existsByPhoneNumber("123456789")).thenReturn(true);

            // Act.
            boolean result = userQueryService.existsByPhoneNumber("123456789");

            // Assert.
            assertTrue(result);
        }

        @Test @DisplayName("Debe retornar false si el teléfono no existe")
        void shouldReturnFalseWhenPhoneNotExists() {
            // Arrange.
            when(userRepository.existsByPhoneNumber("999999999")).thenReturn(false);

            // Act.
            boolean result = userQueryService.existsByPhoneNumber("999999999");

            // Assert.
            assertFalse(result);
        }
    }

    @Nested @DisplayName("Tests para existsByUsername")
    class ExistsByUsernameTests {

        @Test @DisplayName("Debe retornar true si el username existe")
        void shouldReturnTrueWhenUsernameExists() {
            // Arrange.
            when(userRepository.existsByUsername("existente")).thenReturn(true);

            // Act.
            boolean result = userQueryService.existsByUsername("existente");

            // Assert.
            assertTrue(result);
        }

        @Test @DisplayName("Debe retornar false si el username no existe")
        void shouldReturnFalseWhenUsernameNotExists() {
            // Arrange.
            when(userRepository.existsByUsername("noexistente")).thenReturn(false);

            // Act.
            boolean result = userQueryService.existsByUsername("noexistente");

            // Assert.
            assertFalse(result);
        }
    }
}
