package com.cryfirock.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 1. Clase de pruebas de la aplicación de autenticación.
 * 2. Anotación para indicar que es una prueba de Spring Boot.
 * 3. Prueba de carga del contexto de Spring Boot.
 * 4. Utiliza JUnit 5 para las pruebas.
 * 5. Utiliza el perfil "test" para la configuración de H2.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@SpringBootTest
@ActiveProfiles("test")
class AuthApplicationTests {
    @Test
    void contextLoads() {
    }
}
