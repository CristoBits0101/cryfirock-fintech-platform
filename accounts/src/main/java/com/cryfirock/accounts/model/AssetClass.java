package com.cryfirock.accounts.model;

public enum AssetClass {
    FIAT, // Dinero fiduciario (EUR, USD...)
    E_MONEY, // Dinero electrónico (si operas como EMI)
    CRYPTO, // Criptoactivos (BTC, ETH, etc.)
    SECURITY, // Valores/instrumentos (acciones, ETFs, bonos)
    COMMODITY // Opcional: oro tokenizado, commodities, etc.
}
