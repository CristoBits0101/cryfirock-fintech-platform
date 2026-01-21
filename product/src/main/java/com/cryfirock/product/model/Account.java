package com.cryfirock.product.model;

/**
 * Enum que representa los diferentes tipos de cuentas bancarias que un producto puede ofrecer.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-21
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum Account {
    CHECKING,              // Cuenta corriente.
    BASIC_PAYMENT_ACCOUNT, // Cuenta de pago básica.
    SALARY_ACCOUNT,        // Cuenta sueldo.
    SAVINGS,               // Cuenta de ahorro.
    YOUTH,                 // Cuenta joven o estudiante.
    BUSINESS_CHECKING,     // Cuenta corriente de empresa.
    MULTICURRENCY,         // Cuenta multidivisa.
    TERM_DEPOSIT,          // Depósito a plazo fijo.
    NOTICE_ACCOUNT,        // Cuenta con preaviso.
    ESCROW,                // Cuenta de plica y garantía.
    PREPAID_WALLET,        // Monedero prepago.
}
