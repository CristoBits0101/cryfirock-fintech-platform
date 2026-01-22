package com.cryfirock.product.type;

/**
 * Enum que representa los diferentes tipos de trading de derivados por apalancamiento.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-21
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum Derivatives {
    MARGIN,     // Margen.
    FUTURES,    // Futuros.
    PERPETUALS, // Perpetuos.
    OPTIONS     // Opciones.
}
