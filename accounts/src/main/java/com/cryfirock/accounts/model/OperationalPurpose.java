package com.cryfirock.accounts.model;

public enum OperationalPurpose {

    // --- Customer balance semantics ---
    AVAILABLE, // Saldo disponible
    RESERVED, // Reservado (holds)
    PENDING, // Pendiente (capturas, transferencias en curso)
    BLOCKED, // Bloqueado (compliance, disputas)

    // --- Fees ---
    FEE_REVENUE, // Ingreso por comisiones (tu revenue)
    FEE_PAYABLE, // Comisiones a pagar a terceros (PSP, redes, partners)
    FEE_REFUND, // Devoluciones de fees

    // --- Settlement / Clearing / Rails ---
    SETTLEMENT, // Liquidación general
    CLEARING, // Clearing (cámaras/redes)
    NETTING, // Compensación/netting por lotes
    PAYOUTS, // Salidas a banco/cliente
    MERCHANT_SETTLEMENT, // Liquidación a comercios
    INTERCHANGE, // Interchange (tarjetas)

    // --- Accounting / Ops internal ---
    TREASURY, // Tesorería operativa
    RESERVE, // Reservas (seguridad/regulatorio)
    SUSPENSE, // Suspense/transitoria (investigación)
    RECONCILIATION, // Conciliación
    FX_POSITION, // Posición FX
    INTEREST_ACCRUAL, // Devengo intereses
    TAX, // Impuestos
    WRITE_OFF, // Castigos/impagos
    PROVISIONING, // Provisiones (pérdidas esperadas)
    CHARGEBACKS, // Contracargos
    DISPUTES, // Disputas
    LIQUIDATION, // Liquidaciones (margin/futures)
    BRIDGE // Puentes internos (fiat<->emoney, offchain<->onchain)
}
