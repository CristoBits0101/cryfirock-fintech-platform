package com.cryfirock.accounts.model;

public enum ProductType {
    // --- Deposits & Payments (FIAT/E_MONEY) ---
    CHECKING,            // Cuenta corriente
    BASIC_CHECKING,      // Cuenta básica
    PAYROLL,             // Nómina
    SAVINGS,             // Ahorro
    YOUTH,               // Joven/estudiante
    BUSINESS_CHECKING,   // Cuenta empresa
    MULTICURRENCY,       // Multidivisa
    TERM_DEPOSIT,        // Depósito a plazo / CD
    NOTICE_ACCOUNT,      // Ahorro con preaviso
    ESCROW,              // Plica/garantía
    PREPAID_WALLET,      // Monedero prepago

    // --- Cards ---
    DEBIT_CARD,          // Producto de tarjeta débito (si lo modelas como producto)
    CREDIT_CARD,         // Tarjeta crédito

    // --- Credit / Lending ---
    OVERDRAFT,           // Descubierto
    CREDIT_LINE,         // Línea de crédito (revolving)
    PERSONAL_LOAN,       // Préstamo personal
    MORTGAGE,            // Hipoteca
    AUTO_LOAN,           // Préstamo auto
    SME_LOAN,            // Préstamo empresa
    BNPL,                // Buy Now Pay Later

        // --- Investments / Securities ---
    BROKERAGE,           // Cuenta broker
    CUSTODY_SECURITIES,  // Custodia de valores
    MUTUAL_FUNDS,        // Fondos
    PENSION,             // Pensión / jubilación
    // --- Crypto Spot / Custody ---
    CRYPTO_SPOT,         // Spot balance
    CRYPTO_CUSTODY,      // Custodia separada (si aplicas segregación)

    // --- Crypto Yield / Earn ---
    STAKING,             // Staking
    EARN_FLEX,           // Earn flexible
    EARN_LOCKED,         // Earn bloqueado
    LENDING,             // Lending (prestar cripto)
    BORROW,              // Borrow (deuda)
    COLLATERAL,          // Colateral

    // --- Crypto Trading Advanced ---
    MARGIN,              // Margin
    FUTURES,             // Futuros
    PERPETUALS,          // Perpetuos
    OPTIONS,             // Opciones

    // --- Rewards ---
    REWARDS              // Cashback / airdrops / loyalty
}
