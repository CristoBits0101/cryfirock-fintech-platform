package com.cryfirock.auth.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 1. Pruebas unitarias para la clase PasswordUtil.
 * 2. Verifica el correcto funcionamiento de la codificación de contraseñas.
 * 3. Utiliza JUnit 5 para las pruebas.
 */
class PasswordUtilTest {

  @Nested
  @DisplayName("Tests para encodeIfRaw")
  class EncodeIfRawTests {

    @Test
    @DisplayName("Debe retornar null si la contraseña es null")
    void shouldReturnNullWhenPasswordIsNull() {
      // Arrange & Act
      String result = PasswordUtil.encodeIfRaw(null);
      
      // Assert
      assertNull(result);
    }

    @Test
    @DisplayName("Debe codificar una contraseña en texto plano")
    void shouldEncodeRawPassword() {
      // Arrange
      String rawPassword = "miContraseña123";
      
      // Act
      String result = PasswordUtil.encodeIfRaw(rawPassword);
      
      // Assert
      assertNotNull(result);
      assertNotEquals(rawPassword, result);
      assertTrue(result.startsWith("$2a$") || result.startsWith("$2b$") || result.startsWith("$2y$"));
    }

    @Test
    @DisplayName("Debe retornar el mismo hash si ya está codificado con $2a$")
    void shouldReturnSameHashWhen2aPrefix() {
      // Arrange
      String bcryptHash = "$2a$10$N9qo8uLOickgx2ZMRZoMy.Q3LjNBhGGkCcBv3Wv/NQVXUjKKJfWAW";
      
      // Act
      String result = PasswordUtil.encodeIfRaw(bcryptHash);
      
      // Assert
      assertEquals(bcryptHash, result);
    }

    @Test
    @DisplayName("Debe retornar el mismo hash si ya está codificado con $2b$")
    void shouldReturnSameHashWhen2bPrefix() {
      // Arrange
      String bcryptHash = "$2b$10$N9qo8uLOickgx2ZMRZoMy.Q3LjNBhGGkCcBv3Wv/NQVXUjKKJfWAW";
      
      // Act
      String result = PasswordUtil.encodeIfRaw(bcryptHash);
      
      // Assert
      assertEquals(bcryptHash, result);
    }

    @Test
    @DisplayName("Debe retornar el mismo hash si ya está codificado con $2y$")
    void shouldReturnSameHashWhen2yPrefix() {
      // Arrange
      String bcryptHash = "$2y$10$N9qo8uLOickgx2ZMRZoMy.Q3LjNBhGGkCcBv3Wv/NQVXUjKKJfWAW";
      
      // Act
      String result = PasswordUtil.encodeIfRaw(bcryptHash);
      
      // Assert
      assertEquals(bcryptHash, result);
    }

    @Test
    @DisplayName("Debe codificar una cadena vacía")
    void shouldEncodeEmptyString() {
      // Arrange
      String emptyPassword = "";
      
      // Act
      String result = PasswordUtil.encodeIfRaw(emptyPassword);
      
      // Assert
      assertNotNull(result);
      assertTrue(result.startsWith("$2a$") || result.startsWith("$2b$") || result.startsWith("$2y$"));
    }

    @Test
    @DisplayName("Debe generar hashes diferentes para la misma contraseña")
    void shouldGenerateDifferentHashesForSamePassword() {
      // Arrange
      String rawPassword = "testPassword123";
      
      // Act
      String hash1 = PasswordUtil.encodeIfRaw(rawPassword);
      String hash2 = PasswordUtil.encodeIfRaw(rawPassword);
      
      // Assert - BCrypt genera salt aleatorio, por lo que los hashes deben ser diferentes
      assertNotEquals(hash1, hash2);
    }
  }
}
