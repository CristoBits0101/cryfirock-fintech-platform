package com.cryfirock.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity
@Table(
        // Tabla que relaciona cuentas bancarias con productos financieros.
        name = "account_product",
        // 1. Restricción única que asegura que una cuenta bancaria solo pueda tener un producto financiero.
        // 2. Ejemplo: Una cuenta bancaria no puede tener dos productos de tipo "Cuenta de Ahorros".
        uniqueConstraints = {
            @UniqueConstraint(columnNames = { "account_id", "product_id" })
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountProduct {
    // Identificador único del registro de relación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador de la cuenta asociada.
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    // Identificador del producto asociado.
    @Column(name = "product_id", nullable = false)
    private Long productId;
}

