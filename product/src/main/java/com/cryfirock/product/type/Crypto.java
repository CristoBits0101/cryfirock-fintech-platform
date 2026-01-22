package com.cryfirock.product.type;

/**
 * Enum que representa los diferentes tipos de productos de criptomonedas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-21
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum Crypto {
    // ==============================================
    // --- Balance ---
    // ==============================================
    CRYPTO_SPOT,    // Saldo spot.

    // ==============================================
    // --- Custodia ---
    // ==============================================
    CRYPTO_CUSTODY, // Custodia segregada.

    // ==============================================
    // --- Rendimiento ---
    // ==============================================
    STAKING,        // Rendimiento por participación.
    EARN_FLEX,      // Rendimiento flexible.
    EARN_LOCKED,    // Rendimiento bloqueado.

    // ==============================================
    // --- Préstamos ---
    // ==============================================
    LENDING,        // Préstamo cripto a terceros.
    BORROW,         // Deuda de préstamo recibido.

    // ==============================================
    // --- Garantías ---
    // ==============================================
    COLLATERAL      // Colateral y garantía.
}
