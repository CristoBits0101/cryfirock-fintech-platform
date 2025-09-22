package com.cryfirock.auth.service.entities;

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
@AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
        @AttributeOverride(name = "lastLoginAt", column = @Column(name = "last_login_at"))
})
public class Audit {

    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLoginAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
