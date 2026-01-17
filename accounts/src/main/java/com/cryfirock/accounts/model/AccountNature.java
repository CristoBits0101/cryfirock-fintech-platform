package com.cryfirock.accounts.model;

/**
 * 1. Enum que representa la naturaleza o finalidad de una cuenta.
 * 2. Define diferentes tipos de cuentas según su propósito o uso.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AccountNature {
    // Cuenta perteneciente a una persona física o individuo.
    CUSTOMER,
    // Cuenta perteneciente a una persona jurídica u organización.
    BUSINESS,
    // Cuenta perteneciente a una institución financiera.
    INSTITUTIONAL,
    // Cuenta perteneciente a un comerciante o vendedor.
    MERCHANT,
    // Cuenta perteneciente a un socio o colaborador.
    PARTNER,
    // Cuenta perteneciente al sistema o plataforma.
    SYSTEM,
    // Cuenta perteneciente a la tesorería o gestión de fondos.
    TREASURY,
    // Cuenta omnibus que agrupa fondos de múltiples clientes.
    OMNIBUS,
    // Cuenta en custodia o depósito en garantía.
    ESCROW
}
