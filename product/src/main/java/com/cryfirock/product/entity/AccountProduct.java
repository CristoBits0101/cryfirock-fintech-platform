package com.cryfirock.product.entity;

import java.time.LocalDateTime;

import com.cryfirock.product.type.AccountProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1. Clase que representa la relación entre una cuenta y un producto contratado.
 * 2. Permite gestionar múltiples productos activos por cuenta según reglas del catálogo.
 * 3. Almacena el estado y fechas relevantes del producto en la cuenta.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-21
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity
@Table(name = "account_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountProduct {
    // Identificador único de la relación cuenta-producto.
    // Ejemplo: 5001.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador de la cuenta propietaria del producto.
    // Ejemplo: 1001.
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    // Producto asociado a la cuenta.
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Estado actual del producto dentro de la cuenta.
    // Ejemplo: ACTIVE.
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountProductStatus status;

    // Fecha y hora de activación del producto en la cuenta.
    // Ejemplo: 2025-01-21T10:15:30.
    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    // Fecha y hora de cierre del producto en la cuenta.
    // Ejemplo: 2025-06-30T18:00:00.
    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}

