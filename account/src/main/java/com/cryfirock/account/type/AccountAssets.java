package com.cryfirock.account.type;

/**
 * Enum que representa las diferentes clases de activos que una cuenta puede manejar.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AccountAssets {
    // =================================================================
    // --- Activos monetarios ---
    // =================================================================
    FIAT, // Activo fiduciario o moneda tradicional.
    E_MONEY, // Activo electrónico o dinero digital.
    CRYPTO, // Activo criptográfico o moneda digital descentralizada.

    // =================================================================
    // --- Activos de inversión ---
    // =================================================================
    SECURITY, // Activo tangible como bienes raíces o metales preciosos.
    COMMODITY // Activo derivado cuyo valor depende de otro activo.
}
