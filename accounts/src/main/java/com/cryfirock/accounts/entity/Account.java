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
 * 3. Usa Lombok para generar los métodos de acceso y constructores.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity @Table(name = "accounts") @Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Account {
    /**
     * ============================================================================================
     * Registros asociados a la cuenta bancaria.
     * ============================================================================================
     */

    // Refiere al número identificador único de la cuenta en la base de datos.
    // Ejemplo: 1001
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Refiere al número identificador del propietario de la cuenta en la base de datos.
    // Ejemplo: 523
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    /**
     * ============================================================================================
     * Denominaciones asociadas a la cuenta bancaria.
     * ============================================================================================
     */

    // Refiere a la clase de activo financiero de la cuenta bancaria.
    // Ejemplo: CRYPTO para criptomonedas.
    @Enumerated(EnumType.STRING) @Column(name = "asset_class", nullable = false)
    private AccountAssetClass assetClass;

    // Refiere al código de denominación ISO o CRYPTO de la moneda utilizada en la cuenta bancaria.
    // Ejemplo: "USD" para dólares estadounidenses.
    @Column(name = "currency", nullable = false, length = 12)
    private String currency;

    /**
     * ============================================================================================
     * Valores asociados a la cuenta bancaria.
     * ============================================================================================
     */

    // Refiere al número único asociado a la cuenta bancaria.
    // Ejemplo: "ES7620770024003102575766" para el IBAN.
    @Column(name = "account_number", nullable = false, unique = true, length = 34)
    private String accountNumber;

    // Refiere al saldo actual de la cuenta bancaria.
    // Ejemplo: 1500.75
    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    /**
     * ============================================================================================
     * Representaciones asociadas a la cuenta bancaria.
     * ============================================================================================
     */

    // Representa la finalidad de la cuenta bancaria.
    // Ejemplo: CUSTOMER para cuentas de clientes.
    @Enumerated(EnumType.STRING) @Column(name = "nature", nullable = false)
    private AccountNature nature;

    // Representa los estados del saldo de la cuenta bancaria.
    // Ejemplo: PENDING para saldos pendientes.
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
