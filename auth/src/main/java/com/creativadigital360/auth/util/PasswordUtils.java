package com.creativadigital360.auth.util;

import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * ===============================================================================================
 * Paso 10.1: Detecta si una contraseña ya está hasheada en BCrypt y la codifica si no lo está
 * ===============================================================================================
 */
@Component
public class PasswordUtils {

    /**
     * ===========================================================================================
     * Paso 10.2: Atributos
     * ===========================================================================================
     */
    // Componente que hashea y comprueba contraseñas
    // Referencia final no cambia y bean scope singleton misma instancia en petición
    private final PasswordEncoder passwordEncoder;

    // Predicate configurado que detecta si es BCrypt por prefijo
    // Referencia final no cambia y bean scope singleton misma instancia en petición
    // Predicate static para mantener una única copia por clase
    // Varios beans singleton de la misma clase comparten la misma variable estática
    // Los miembros estáticos se usan sin instanciar importando la clase
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

    // Codifica la contraseña solo si aún no está hasheada
    public String encodeIfRaw(String rawOrHash) {
        if (rawOrHash == null)
            return null;
        // El Predicate evalúa el valor contra el código de test configurado
        // Predicado que verifica si la cadena tiene prefijo BCrypt
        return IS_BCRYPT.test(rawOrHash) ? rawOrHash : passwordEncoder.encode(rawOrHash);
    }

}
