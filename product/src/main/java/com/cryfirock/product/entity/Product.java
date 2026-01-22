package com.cryfirock.product.entity;

import com.cryfirock.product.type.ProductCategory;
import com.cryfirock.product.type.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1. Clase que representa el catálogo de productos financieros de la plataforma.
 * 2. Define reglas de disponibilidad y límites por cuenta.
 * 3. Centraliza metadatos comunes para cualquier familia de producto.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-21
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    // Identificador único del producto en la base de datos.
    // Ejemplo: 1001.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código interno para identificar el producto de forma única.
    // Ejemplo: ACC_CHECKING.
    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    // Nombre comercial del producto.
    // Ejemplo: Cuenta Corriente.
    @Column(name = "name", nullable = false, length = 120)
    private String name;

    // Descripción funcional del producto.
    // Ejemplo: Cuenta bancaria para operaciones diarias.
    @Column(name = "description", length = 500)
    private String description;

    // Familia principal del producto.
    // Ejemplo: ACCOUNT.
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 40)
    private ProductCategory category;

    // Subtipo específico dentro de la familia del producto.
    // Ejemplo: CHECKING.
    @Column(name = "sub_type", nullable = false, length = 60)
    private String subType;

    // Número máximo de productos activos por cuenta para este subtipo.
    // Ejemplo: 1.
    @Column(name = "max_active_per_account", nullable = false)
    private Integer maxActivePerAccount;

    // Indica si la cuenta puede tener más de un producto activo del mismo subtipo.
    // Ejemplo: false.
    @Column(name = "allows_multiple", nullable = false)
    private Boolean allowsMultiple;

    // Estado de disponibilidad del producto en la plataforma.
    // Ejemplo: ACTIVE.
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProductStatus status;
}

