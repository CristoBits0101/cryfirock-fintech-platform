package com.cryfirock.account.util;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Utilidades de validación.
 * 
 * @Component: Indica que es un componente de Spring que puede ser inyectado.
 * 
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-25
 */
@Component
public class ValidationUtil {
    /**
     * Retorna una lista segura de valores.
     * 
     * @param values Lista de valores.
     * @return Lista segura de valores.
     */
    public static List<Long> safeList(List<Long> values) {
        return values == null ? List.of() : values;
    }
}
