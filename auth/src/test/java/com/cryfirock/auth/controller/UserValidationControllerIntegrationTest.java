package com.cryfirock.auth.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.cryfirock.auth.repository.JpaUserRepository;

import jakarta.persistence.EntityManager;

/**
 * 1. Tests de integración para el controlador de validaciones.
 * 2. Prueba endpoints de verificación de existencia.
 * 3. Usa MockMvc para simular peticiones HTTP.
 * 4. Verifica respuestas booleanas correctas.
 * 5. Requiere autenticación para acceder a los endpoints.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test") @Transactional @SuppressWarnings("unused")
class UserValidationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        entityManager.flush();
    }

    @Nested @DisplayName("Tests para GET /api/validations/exists?email=")
    class CheckEmailExistsTests {

        @Test @WithMockUser(roles = "USER") @DisplayName("Debe retornar true cuando el email existe")
        void shouldReturnTrueWhenEmailExists() throws Exception {
            createTestUserNative("test@exists.com", "611111111", "testuser1");

            mockMvc.perform(get("/api/validations/exists").param("email", "test@exists.com"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }

        @Test @WithMockUser(roles = "USER") @DisplayName("Debe retornar false cuando el email no existe")
        void shouldReturnFalseWhenEmailNotExists() throws Exception {
            mockMvc.perform(get("/api/validations/exists").param("email", "noexiste@test.com"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        }
    }

    @Nested @DisplayName("Tests para GET /api/validations/exists?username=")
    class CheckUsernameExistsTests {

        @Test @WithMockUser(roles = "USER") @DisplayName("Debe retornar true cuando el username existe")
        void shouldReturnTrueWhenUsernameExists() throws Exception {
            createTestUserNative("user@test.com", "633333333", "usuarioexistente");

            mockMvc.perform(get("/api/validations/exists").param("username", "usuarioexistente"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }

        @Test @WithMockUser(roles = "USER") @DisplayName("Debe retornar false cuando el username no existe")
        void shouldReturnFalseWhenUsernameNotExists() throws Exception {
            mockMvc.perform(get("/api/validations/exists").param("username", "usuariofantasma"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        }
    }

    @Nested @DisplayName("Tests para GET /api/validations/exists?phoneNumber=")
    class CheckPhoneExistsTests {

        @Test @WithMockUser(roles = "USER") @DisplayName("Debe retornar true cuando el teléfono existe")
        void shouldReturnTrueWhenPhoneExists() throws Exception {
            createTestUserNative("phone@test.com", "644444444", "phoneuser");

            mockMvc.perform(get("/api/validations/exists").param("phoneNumber", "644444444"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }

        @Test @WithMockUser(roles = "USER") @DisplayName("Debe retornar false cuando el teléfono no existe")
        void shouldReturnFalseWhenPhoneNotExists() throws Exception {
            mockMvc.perform(get("/api/validations/exists").param("phoneNumber", "999999999"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        }
    }

    @Nested @DisplayName("Tests de seguridad")
    class SecurityTests {

        @Test @DisplayName("Debe retornar 401 sin autenticación en email")
        void shouldReturn401WithoutAuthOnEmail() throws Exception {
            mockMvc.perform(get("/api/validations/exists").param("email", "test@test.com"))
                    .andExpect(status().isUnauthorized());
        }

        @Test @DisplayName("Debe retornar 401 sin autenticación en username")
        void shouldReturn401WithoutAuthOnUsername() throws Exception {
            mockMvc.perform(get("/api/validations/exists").param("username", "testuser"))
                    .andExpect(status().isUnauthorized());
        }

        @Test @DisplayName("Debe retornar 401 sin autenticación en phone")
        void shouldReturn401WithoutAuthOnPhone() throws Exception {
            mockMvc.perform(get("/api/validations/exists").param("phoneNumber", "600000000"))
                    .andExpect(status().isUnauthorized());
        }
    }

    /**
     * Crea un usuario de prueba usando SQL nativo para evitar validaciones de
     * entidad.
     */
    private void createTestUserNative(String email, String phoneNumber, String username) {
        entityManager.createNativeQuery(
                "INSERT INTO users (given_name, family_name, email, phone_number, username, password_hash, address, dob, account_status) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .setParameter(1, "Test")
                .setParameter(2, "User")
                .setParameter(3, email)
                .setParameter(4, phoneNumber)
                .setParameter(5, username)
                .setParameter(6, "$2a$10$hashedpassword")
                .setParameter(7, "Dirección Test 123")
                .setParameter(8, LocalDate.of(1990, 1, 1))
                .setParameter(9, "ACTIVE")
                .executeUpdate();
        entityManager.flush();
    }
}
