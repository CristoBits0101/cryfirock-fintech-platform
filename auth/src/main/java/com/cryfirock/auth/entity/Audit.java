package com.cryfirock.auth.entity;

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
 * 1. Genera un constructor con todos los argumentos.
 * 2. Genera un constructor sin argumentos.
 * 3. Genera getters, setters, toString, hashCode y equals.
 * 4. Clase embebible que contiene atributos de auditoría para entidades JPA.
 * 5. Mapea estos atributos a columnas con otro nombre en la tabla.
 * 6. Algunos de estos atributos no se pueden actualizar o no pueden ser nulos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
    @AttributeOverride(name = "lastLoginAt", column = @Column(name = "last_login_at"))
})
public class Audit {
    /**
     * 1. Sirve para marcar la fecha de creación del registro.
     * 2. Sirve para marcar la fecha de la última actualización del registro.
     * 3. Sirve para marcar la fecha del último inicio de sesión.
     * 4. Instant es una clase que representa fecha y hora en UTC.
     */
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLoginAt;

    /**
     * 1. Método que se ejecuta antes de persistir la entidad.
     * 2. Asigna la fecha y hora actual a createdAt y updatedAt.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * 1. Método que se ejecuta antes de actualizar la entidad.
     * 2. Asigna la fecha y hora actual a updatedAt.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
