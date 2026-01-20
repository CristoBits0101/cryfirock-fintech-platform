package com.cryfirock.accounts.model;

/**
 * Enum que representa los diferentes tipos de productos financieros que una cuenta ofrece.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AccountProduct {
    // ==========================================================
    // --- Cuentas bancarias ---
    // ==========================================================
    CHECKING,               // Cuenta corriente.
    BASIC_PAYMENT_ACCOUNT,  // Cuenta de pago básica.
    SALARY_ACCOUNT,         // Cuenta sueldo.
    SAVINGS,                // Cuenta de ahorro.
    YOUTH,                  // Cuenta joven o estudiante.
    BUSINESS_CHECKING,      // Cuenta corriente de empresa.
    MULTICURRENCY,          // Cuenta multidivisa.
    TERM_DEPOSIT,           // Depósito a plazo fijo.
    NOTICE_ACCOUNT,         // Cuenta con preaviso.
    ESCROW,                 // Cuenta de plica y garantía.
    PREPAID_WALLET,         // Monedero prepago.

    // ==========================================================
    // --- Tarjetas ---
    // ==========================================================
    DEBIT_CARD,             // Tarjeta débito.
    CREDIT_CARD,            // Tarjeta crédito.

    // ==========================================================
    // --- Crédito y financiación ---
    // ==========================================================
    OVERDRAFT,              // Descubierto.
    CREDIT_LINE,            // Línea de crédito revolving.
    PERSONAL_LOAN,          // Préstamo personal.
    MORTGAGE,               // Hipoteca.
    AUTO_LOAN,              // Préstamo para automóvil.
    SME_LOAN,               // Préstamo para empresa.
    BNPL,                   // Compra ahora y paga después.

    // ==========================================================
    // --- Inversión tradicional ---
    // ==========================================================
    BROKERAGE,              // Cuenta de bróker.
    CUSTODY_SECURITIES,     // Custodia de valores.
    MUTUAL_FUNDS,           // Fondos de inversión.
    PENSION,                // Pensión y jubilación.

    // ==========================================================
    // --- Cripto: Balances y custodia ---
    // ==========================================================
    CRYPTO_SPOT,            // Saldo spot.
    CRYPTO_CUSTODY,         // Custodia separada por segregación.

    // ==========================================================
    // --- Cripto: Rendimiento y préstamos ---
    // ==========================================================
    STAKING,                // Rendimiento de participación.
    EARN_FLEX,              // Rendimiento flexible.
    EARN_LOCKED,            // Rendimiento bloqueado.
    LENDING,                // Préstamo cripto a terceros.
    BORROW,                 // Deuda de préstamo recibido.
    COLLATERAL,             // Colateral y garantía.

    // ==========================================================
    // --- Trading de derivados ---
    // ==========================================================
    MARGIN,                 // Margen.
    FUTURES,                // Futuros.
    PERPETUALS,             // Perpetuos.
    OPTIONS,                // Opciones.

    // ==========================================================
    // --- Fidelización ---
    // ==========================================================
    REWARDS                 // Recompensas y fidelización.
}
