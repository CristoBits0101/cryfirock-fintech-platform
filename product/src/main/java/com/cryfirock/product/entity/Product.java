package com.cryfirock.product.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
 * @since 2025-01-22
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity @Table(name = "product") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product {
    // Identificador único del producto en la base de datos.
    // Ejemplo: 1001
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto.
    private String name;

    // Metadatos de manipulación de la cuenta bancarias.
    // Ejemplo: 07/01/2025 10:15:30
    @Embedded
    private Audit audit;
}
