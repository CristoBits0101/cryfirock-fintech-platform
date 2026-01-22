package com.cryfirock.account.type;

/**
 * 1. Enum que representa la naturaleza o finalidad de una cuenta.
 * 2. Define diferentes tipos de cuentas según su propósito o uso.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AccountNature {
    // ============================================================================
    // --- Finalidad según cliente ---
    // ============================================================================
    CUSTOMER,        // Cuenta perteneciente a una persona física o individuo.
    BUSINESS,        // Cuenta perteneciente a una persona jurídica u organización.
    MERCHANT,        // Cuenta perteneciente a un comerciante o vendedor.
    PARTNER,         // Cuenta perteneciente a un socio o colaborador.
    INSTITUTIONAL,   // Cuenta perteneciente a una institución financiera.

    // ============================================================================
    // --- Finalidad según institución ---
    // ============================================================================
    SYSTEM,          // Cuenta perteneciente al sistema o plataforma.
    TREASURY,        // Cuenta perteneciente a la tesorería o gestión de fondos.

    // ============================================================================
    // --- Finalidad según custodia ---
    // ============================================================================
    OMNIBUS,         // Cuenta ómnibus que agrupa fondos de múltiples clientes.
    ESCROW           // Cuenta en custodia o depósito en garantía.
}
