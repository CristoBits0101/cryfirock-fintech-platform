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
 * @since 2025-01-17
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

    // 1. Refiere al número identificador del propietario de la cuenta en la base de datos.
    // 2. Ejemplo: 523
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    // ============================================================================================
    // --- Denominaciones asociadas a la cuenta bancaria ---
    // ============================================================================================
    // 1. Refiere a la clase de activo financiero de la cuenta bancaria.
    // 2. Ejemplo: CRYPTO
    @Enumerated(EnumType.STRING) @Column(name = "asset_class", nullable = false)
    private AccountAssets asset;

    // 1. Refiere al código de denominación ISO o CRYPTO de la moneda utilizada en la cuenta bancaria.
    // 2. Ejemplo: USD
    @Column(name = "currency", nullable = false, length = 12)
    private String currency;

    // ============================================================================================
    // --- Valores asociados a la cuenta bancaria ---
    // ============================================================================================
    // 1. Refiere al número único IBAN asociado a la cuenta bancaria.
    // 2. Ejemplo: ES7620770024003102575766
    @Column(name = "account_number", nullable = false, unique = true, length = 34)
    private String number;

    // 1. Refiere al saldo actual de la cuenta bancaria.
    // 2. Ejemplo: 1500.75
    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    // ============================================================================================
    // --- Representaciones asociadas a la cuenta bancaria ---
    // ============================================================================================
    // 1. Representa la finalidad de la cuenta bancaria.
    // 2. Ejemplo: CUSTOMER
    @Enumerated(EnumType.STRING) @Column(name = "nature", nullable = false)
    private AccountNature nature;

    // 1. Representa los estados del saldo de la cuenta bancaria.
    // 2. Ejemplo: PENDING
    @Enumerated(EnumType.STRING) @Column(name = "operational", nullable = false)
    private AccountOperational operational;

    // 1. Representa el estado actual de la cuenta bancaria.
    // 2. Ejemplo: ACTIVE
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false)
    private AccountStatus status;

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
        if (this.status == null) this.status = AccountStatus.ACTIVE;
        if (this.balance == null) this.balance = BigDecimal.ZERO;
    }
}
