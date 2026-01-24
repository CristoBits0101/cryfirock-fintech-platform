package com.cryfirock.product.entity;

import com.cryfirock.product.type.ProductCategory;
import com.cryfirock.product.type.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
 * @since 2026-01-24
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity @Table(name = "product") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product {
    // ============================================================================================
    // --- Registros asociados al producto ---
    // ============================================================================================
    // 1. Identificador único del producto en la base de datos.
    // 2. Ejemplo: 1001
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================================================
    // --- Datos del producto ---
    // ============================================================================================
    // 1. Nombre del producto.
    // 2. Ejemplo: CRYPTO_CUSTODY
    @Column(nullable = false, length = 50) @NotEmpty @Size(max = 50)
    private String name;

    // 1. Descripción del producto.
    // 2. Ejemplo: Cuenta de custodia de criptomonedas.
    @Lob @Column(nullable = false, length = 1000, columnDefinition = "TEXT") @NotEmpty
    private String description;

    // ============================================================================================
    // --- Identificación del producto ---
    // ============================================================================================
    // 1. Código único del producto.
    // 2. Ejemplo: 1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa
    @Column(nullable = false, length = 50, unique = true) @NotEmpty @Size(max = 50)
    private String code;

    // 1. Categoría del producto.
    // 2. Ejemplo: CRYPTO
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 50) @NotNull
    private ProductCategory category;

    // ============================================================================================
    // --- Estado del producto ---
    // ============================================================================================
    // 1. Estado del producto.
    // 2. Ejemplo: ACTIVE
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 50) @NotNull
    private ProductStatus status;

    // 1. Metadatos de manipulación de la cuenta bancarias.
    // 2. Ejemplo: 07/01/2025 10:15:30
    @Embedded
    private Audit audit;
}
