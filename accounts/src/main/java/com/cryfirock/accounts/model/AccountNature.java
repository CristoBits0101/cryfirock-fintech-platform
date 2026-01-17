package com.cryfirock.accounts.model;

/**
 * 1. Enumera las posibles naturalezas de una cuenta.
 * 2. Ayuda a categorizar las cuentas según su propósito y usuario final.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AccountNature {
    /**
     * 1. Cuenta de cliente para persona física (retail).
     * - Personal (uso particular).
     * - Individual (titular único).
     */
    CUSTOMER,
    /**
     * 2. Cuenta de cliente para persona jurídica.
     * - Negocio (autónomos y pequeñas empresas).
     * - Corporativo (empresa mediana y grande).
     * - PYME (pequeña y mediana empresa).
     */
    BUSINESS,
    /**
     * 3. Cuenta interna de la institución financiera.
     * - Banco ().
     * - Fondos propios ().
     * - Brokers ().
     */
    INSTITUTIONAL,
    /**
    *
    */
    MERCHANT,
    /**
    *
    */
    PARTNER,
    /**
    *
    */
    SYSTEM,
    /**
    *
    */
    TREASURY,
    /**
    *
    */
    OMNIBUS,
    /**
    *
    */
    ESCROW
}
