package com.cryfirock.account.entity;

import com.cryfirock.account.type.AccountProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1. Entidad que relaciona cuentas con productos.
 * 2. Guarda el identificador del producto del microservicio product.
 * 3. Representa la relación muchos a muchos entre cuentas y productos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
@Entity @Table(
        // Tabla que relaciona cuentas bancarias con productos financieros.
        name = "account_product",
        // 1. Restricción de un código de cuenta y un código de producto.
        // 2. Ejemplo: No se puede tener dos productos de tipo "Cuenta de Ahorros".
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "account_id", "product_id" })
        }) @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountProduct {
    // 1. En la base de datos no existe un id autogenerado.
    // 2. Todas las entidades deben tener un id autogenerado.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. Identificador de la cuenta asociada.
    // 2. Ejemplo: 1001
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    // 1. Identificador del producto asociado.
    // 2. Ejemplo: 523
    @Column(name = "product_id", nullable = false)
    private Long productId;

    // 1. Estado de la vigencia del producto en la cuenta.
    // 2. Ejemplo: "ACTIVE"
    @Enumerated(EnumType.STRING) @Column(name = "product_status", nullable = false)
    private AccountProductStatus productStatus;

    // 1. Metadatos de manipulación de la cuenta bancaria.
    // 2. Fecha en la que el producto se relaciona con la cuenta.
    // 3. Ejemplo: 07/01/2025 10:15:30
    @Embedded
    private Audit audit;

    /**
     * Constructor personalizado.
     *
     * @param accountId Identificador de la cuenta.
     * @param productId Identificador del producto.
     * @param productStatus Estado del producto.
     */
    public AccountProduct(Long accountId, Long productId, AccountProductStatus productStatus) {
        this.accountId = accountId;
        this.productId = productId;
        this.productStatus = productStatus;
    }
}
