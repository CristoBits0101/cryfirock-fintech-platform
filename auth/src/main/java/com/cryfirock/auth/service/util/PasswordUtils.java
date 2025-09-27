package com.cryfirock.auth.service.util;

import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ===============================================================================================
 * Paso 10.1:
 * ===============================================================================================
 */

public class PasswordUtils {

    /**
     * ===========================================================================================
     * Paso 10.2: Atributos
     * ===========================================================================================
     */

    // Componente que hashea y comprueba contraseñas
    private final PasswordEncoder passwordEncoder;

    // Predicate configurado que detecta si es BCrypt por prefijo
    private static final Predicate<String> IS_BCRYPT = startsWithAny("$2a$", "$2b$", "$2y$");

    /**
     * ===========================================================================================
     * Paso 10.3: Constructores
     * ===========================================================================================
     */

    // Inicializa el atributo al instanciar la clase
    public PasswordUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ===========================================================================================
     * Paso 10.4: Helpers
     * ===========================================================================================
     */

    // Test que ejecutará el predicado
    // Detecta si la contraseña ya es hash BCrypt por prefijo
    private static Predicate<String> startsWithAny(String... p) {
        return s -> s != null && Arrays.stream(p).anyMatch(s::startsWith);
    }

    public String encodeIfRaw(String rawOrHash) {
        if (rawOrHash == null) return null;
        // El Predicate evalúa el valor contra el código de test configurado
        // Predicado que verifica si la cadena tiene prefijo BCrypt
        return IS_BCRYPT.test(rawOrHash) ? rawOrHash : passwordEncoder.encode(rawOrHash);
    }

}
