package com.creativadigital360.auth.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ========================================================================================================================
 * Paso 4.1: Definición de la clase Audit que se embebe en la entidad User
 * ========================================================================================================================
 */
// Genera constructor con todos los campos
@AllArgsConstructor
// Genera constructor vacío
@NoArgsConstructor
// Genera Getters y Setters, equals/hashCode y toString
@Data
// Marca la clase como embebible dentro de otra entidad JPA
@Embeddable
// Sobrescribe la forma en que JPA mapea los atributos de la clase embebida
@AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
        @AttributeOverride(name = "lastLoginAt", column = @Column(name = "last_login_at"))
})
public class Audit {

    /**
     * ====================================================================================================================
     * Paso 4.2: Columnas
     * ====================================================================================================================
     */
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLoginAt;

    /**
     * ====================================================================================================================
     * Paso 4.3: Métodos
     * ====================================================================================================================
     */
    // Inicializa los atributos antes de almacenarlos
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    // Inicializa el atributo antes de actualizarlo
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
