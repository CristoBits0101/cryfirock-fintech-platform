package com.cryfirock.account.entity;

import java.math.BigDecimal;

import com.cryfirock.account.type.AccountAssets;
import com.cryfirock.account.type.AccountNature;
import com.cryfirock.account.type.AccountOperational;
import com.cryfirock.account.type.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1. Clase que representa una cuenta en el sistema.
 * 2. Mapea la entidad de cuenta a la tabla account en la base de datos.
 * 3. Usa Lombok para generar los métodos de acceso y constructores.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-25
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity @Table(name = "account") @Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Account {
    // ============================================================================================
    // --- Registros asociados a la cuenta bancaria ---
    // ============================================================================================
    // 1. Refiere al número identificador único de la cuenta en la base de datos.
    // 2. Ejemplo: 1001
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. Refiere al número identificador único del usuario principal de la cuenta.
    // 2. Ejemplo: 523
    @Column(name = "main_owner_id", nullable = false)
    private Long mainOwnerId;

    // ============================================================================================
    // --- Denominaciones asociadas a la cuenta bancaria ---
    // ============================================================================================
    // 1. Refiere a la clase de activo financiero de la cuenta bancaria.
    // 2. Ejemplo: CRYPTO
    @Enumerated(EnumType.STRING) @Column(name = "asset_class", nullable = false)
    private AccountAssets financialAssetClass;

    // 1. Refiere al código de denominación ISO o CRYPTO de la moneda utilizada en la cuenta.
    // 2. Ejemplo: USD
    @Column(name = "currency_code", nullable = false, length = 12)
    private String currencyCode;

    // ============================================================================================
    // --- Valores asociados a la cuenta bancaria ---
    // ============================================================================================
    // 1. Refiere al número único IBAN asociado a la cuenta bancaria.
    // 2. Ejemplo: ES7620770024003102575766
    @Column(name = "iban_number", nullable = false, unique = true, length = 34)
    private String ibanNumber;

    // 1. Refiere al saldo actual de la cuenta bancaria.
    // 2. Ejemplo: 1500.75
    @Column(name = "current_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal currentBalance;

    // ============================================================================================
    // --- Representaciones asociadas a la cuenta bancaria ---
    // ============================================================================================
    // 1. Representa la finalidad de la cuenta bancaria.
    // 2. Ejemplo: CUSTOMER
    @Enumerated(EnumType.STRING) @Column(name = "bank_account_purpose", nullable = false)
    private AccountNature bankAccountPurpose;

    // 1. Representa los estados del saldo de la cuenta bancaria.
    // 2. Ejemplo: PENDING
    @Enumerated(EnumType.STRING) @Column(name = "bank_account_operational", nullable = false)
    private AccountOperational bankAccountOperational;

    // 1. Representa el estado actual de la cuenta bancaria.
    // 2. Ejemplo: ACTIVE
    @Enumerated(EnumType.STRING) @Column(name = "bank_account_status", nullable = false)
    private AccountStatus bankAccountStatus;

    // ============================================================================================
    // --- Metadatos asociados a la cuenta bancaria ---
    // ============================================================================================
    // 1. Metadatos de manipulación de la cuenta bancarias.
    // 2. Ejemplo: 07/01/2025 10:15:30
    @Embedded
    private Audit audit;

    // 1. Metadatos para establecer valores predeterminados.
    // 2. Ejemplo: ACTIVE
    @PrePersist
    public void prePersist() {
        if (this.audit == null) this.audit = new Audit();
        this.audit.prePersist();
        if (this.bankAccountStatus == null) this.bankAccountStatus = AccountStatus.ACTIVE;
        if (this.currentBalance == null) this.currentBalance = BigDecimal.ZERO;
    }

    @PreUpdate
    public void preUpdate() {
        if (this.audit == null) this.audit = new Audit();
        this.audit.preUpdate();
    }
}
