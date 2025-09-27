package com.cryfirock.auth.service.entity;

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
 * ========================================================================================================================
 * Paso 4.1: Definición de la clase Audit que se embebe en la entidad User
 * ========================================================================================================================
 */

// Genera constructor con todos los campos
@AllArgsConstructor
// Genera constructor vacío
@NoArgsConstructor
// Genera Getters y Setters para todos los atributos
// Genera métodos equals() y hashCode() para comparar instancias
// Genera método toString() para representar el objeto como texto
// Genera Constructor con atributos final o @NonNull si existieran
@Data
// Marca la clase como embebible dentro de otra entidad JPA
// Esto significa que sus atributos no viven en una tabla propia
// Se insertan como columnas en la tabla de la entidad que la usa
// Se combina con la anotación @Embedded en la entidad principal
@Embeddable
// JPA mapea atributo a columna cuando no coinciden
// Restricciones aplicadas al generar la tabla desde JPA
// Sobrescribe la forma en que JPA mapea los atributos de la clase embebida
// Cada @AttributeOverride redefine el nombre de la columna y sus propiedades
// En la tabla de la entidad principal
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
