package com.cryfirock.auth.util;

import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 1. Utilidad para el manejo de contraseñas.
 * 2. Proporciona métodos para codificar contraseñas.
 * 3. Proporciona métodos para verificar si una cadena ya está codificada.
 * 4. Utiliza BCrypt para la codificación de contraseñas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public final class PasswordUtil {
    // 1. Codificador de contraseñas utilizando BCrypt.
    // 2. Predicado para verificar si una cadena es un hash BCrypt.
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private static final Predicate<String> IS_BCRYPT = startsWithAny("$2a$", "$2b$", "$2y$");

    /**
     * 1. Crea un predicado para verificar comienzos de cadenas.
     * 2. Utilizado para identificar hashes BCrypt.
     * 3. Retorna verdadero si la cadena comienza con alguno de los prefijos dados.
     *
     * @param p Prefijos a verificar.
     * @return Predicado que verifica los prefijos.
     */
    private static Predicate<String> startsWithAny(String... p) {
        return s -> s != null && Arrays.stream(p).anyMatch(s::startsWith);
    }

    /**
     * 1. Verifica si la cadena es un hash BCrypt.
     * 2. Si es un hash lo retorna tal cual y si no lo codifica.
     *
     * @param rawOrHash Cadena que puede ser contraseña en texto plano o hash.
     * @return Hash BCrypt de la contraseña.
     */
    public static String encodeIfRaw(String rawOrHash) {
        if (rawOrHash == null) return null;
        return IS_BCRYPT.test(rawOrHash)
                ? rawOrHash
                : ENCODER.encode(rawOrHash);
    }
}
