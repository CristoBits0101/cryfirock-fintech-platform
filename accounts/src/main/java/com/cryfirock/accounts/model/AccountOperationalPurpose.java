package com.cryfirock.accounts.model;

/**
 * Enum que representa la finalidad operativa de un saldo o movimiento dentro del sistema.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum AccountOperationalPurpose {
    // ===========================================================================================
    // --- Semántica del saldo del cliente ---
    // ===========================================================================================
    AVAILABLE,           // Saldo disponible.
    RESERVED,            // Reservado con retenciones.
    PENDING,             // Pendiente por capturas o transferencias en curso.
    BLOCKED,             // Bloqueado para cumplimiento normativo o por disputas.

    // ===========================================================================================
    // --- Comisiones ---
    // ===========================================================================================
    FEE_REVENUE,         // Ingreso por comisiones.
    FEE_PAYABLE,         // Comisiones a pagar a terceros de PSP y redes o socios.
    FEE_REFUND,          // Devolución de comisiones.

    // ===========================================================================================
    // --- Liquidación, compensación y rieles de pago ---
    // ===========================================================================================
    SETTLEMENT,          // Liquidación general.
    CLEARING,            // Compensación de cámaras o redes.
    NETTING,             // Compensación neta por lotes.
    PAYOUTS,             // Salidas hacia banco o cliente.
    MERCHANT_SETTLEMENT, // Liquidación a comercios.
    INTERCHANGE,         // Intercambio de tasas de tarjetas.

    // ===========================================================================================
    // --- Contabilidad e internas operativas ---
    // ===========================================================================================
    TREASURY,            // Tesorería operativa.
    RESERVE,             // Reservas de seguridad o regulación.
    SUSPENSE,            // Cuenta transitoria en investigación.
    RECONCILIATION,      // Conciliación.
    FX_POSITION,         // Posición de divisa en FX.
    INTEREST_ACCRUAL,    // Devengo de intereses.
    TAX,                 // Impuestos.
    WRITE_OFF,           // Castigos por impago.
    PROVISIONING,        // Provisiones de pérdidas esperadas.
    CHARGEBACKS,         // Contracargos.
    DISPUTES,            // Disputas.
    LIQUIDATION,         // Liquidaciones de margen o futuros.
    BRIDGE               // Puente interno entre fiat y dinero electrónico u off-chain y on-chain.
}
