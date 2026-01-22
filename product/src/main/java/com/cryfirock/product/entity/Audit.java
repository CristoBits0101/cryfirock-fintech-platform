package com.cryfirock.product.entity;

import java.time.Instant;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1. Clase embebible que contiene atributos de auditoría para las entidades.
 * 2. Proporciona campos para rastrear la creación y actualización de registros.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-22
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@AllArgsConstructor @NoArgsConstructor @Data @Embeddable @AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at"))
})
public class Audit {
    // 1. Sirve para marcar la fecha de creación del registro.
    // 2. Instant es una clase que representa fecha y hora en UTC.
    private Instant createdAt;

    // 1. Sirve para marcar la fecha de la última actualización del registro.
    // 2. Instant es una clase que representa fecha y hora en UTC.
    private Instant updatedAt;

    // 1. Método que se ejecuta antes de persistir la entidad.
    // 2. Asigna la fecha y hora actual a createdAt y updatedAt.
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    // 1. Método que se ejecuta antes de actualizar la entidad.
    // 2. Asigna la fecha y hora actual a updatedAt.
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
