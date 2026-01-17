package com.cryfirock.accounts.model;

/**
 * 1. Enum que representa las diferentes clases de activos que una cuenta puede manejar.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AssetClass {
    // Activo fiduciario o moneda tradicional.
    FIAT,
    // Activo electrónico o dinero digital.
    E_MONEY,
    // Activo criptográfico o moneda digital descentralizada.
    CRYPTO,
    // Activo tangible como bienes raíces o metales preciosos.
    SECURITY,
    // Activo derivado cuyo valor depende de otro activo.
    COMMODITY
}
