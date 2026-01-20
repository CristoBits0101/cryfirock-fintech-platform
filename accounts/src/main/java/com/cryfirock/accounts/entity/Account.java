package com.cryfirock.accounts.entity;

import java.math.BigDecimal;

import com.cryfirock.accounts.model.AccountAssetClass;
import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountOperationalPurpose;
import com.cryfirock.accounts.model.AccountProductType;
import com.cryfirock.accounts.model.AccountStatus;

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
 * 2. Mapea la entidad de cuenta a la tabla accounts en la base de datos.
 * 3. Usa Lombok para getters, setters y constructores.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity @Table(name = "accounts") @Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Account {
    /**
     * 1. Identificador único de la cuenta.
     * 2. Generación automática por la base de datos.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 1. Identificador del propietario de la cuenta.
     * 2. Referencia al usuario dueño de la cuenta.
     */
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    /**
     * 1. Número de cuenta único en el sistema.
     * 2. Formato alfanumérico generado por el sistema.
     */
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    /**
     * 1. Código de moneda en la que se maneja la cuenta.
     * 2. Formato ISO 4217 en 3 letras USE o EUR o similar.
     */
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    /**
     * 1. Saldo disponible de la cuenta.
     * 2. Precisión de 19 dígitos con 4 decimales.
     */
    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    /**
     * 1. Clase de activo que maneja la cuenta.
     * 2. Define si es FIAT o CRYPTO o E_MONEY...
     */
    @Enumerated(EnumType.STRING) @Column(name = "asset_class", nullable = false)
    private AccountAssetClass assetClass;

    /**
     * 1. Naturaleza o finalidad de la cuenta.
     * 2. Define el tipo de cliente o propósito de la cuenta.
     */
    @Enumerated(EnumType.STRING) @Column(name = "nature", nullable = false)
    private AccountNature nature;

    /**
     * 1. Propósito operativo del saldo de la cuenta.
     * 2. Define si es AVAILABLE, RESERVED, PENDING, etc.
     */
    @Enumerated(EnumType.STRING) @Column(name = "operational_purpose", nullable = false)
    private AccountOperationalPurpose operationalPurpose;

    /**
     * 1. Tipo de producto financiero de la cuenta.
     * 2. Define si es CHECKING, SAVINGS, CRYPTO_SPOT, etc.
     */
    @Enumerated(EnumType.STRING) @Column(name = "product_type", nullable = false)
    private AccountProductType productType;

    /**
     * 1. Estado actual de la cuenta.
     * 2. Indica si la cuenta está activa, suspendida o cerrada.
     */
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false)
    private AccountStatus status;

    /**
     * 1. Atributos de auditoría para la entidad Account.
     * 2. Contiene fechas de creación y actualización.
     */
    @Embedded
    private Audit audit;

    /**
     * 1. Método que se ejecuta antes de persistir la entidad.
     * 2. Asigna valores por defecto a status y balance.
     */
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = AccountStatus.ACTIVE;
        }
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }
    }
}
