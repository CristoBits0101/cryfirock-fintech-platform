package com.cryfirock.product.model;

/**
 * Enum que representa los diferentes tipos de préstamos que un producto puede ofrecer.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-21
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum Credit {
    PERSONAL_LOAN, // Préstamo personal.
    MORTGAGE,      // Hipoteca.
    AUTO_LOAN,     // Préstamo para automóvil.
    SME_LOAN,      // Préstamo para empresa.
    BNPL,          // Compra ahora y paga después.
    OVERDRAFT,     // Descubierto.
    CREDIT_LINE    // Línea de crédito revolving.
}
