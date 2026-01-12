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

// Genera un constructor con todos los argumentos.
@AllArgsConstructor
// Genera un constructor sin argumentos.
@NoArgsConstructor
// Genera getters, setters, toString, hashCode y equals.
@Data
// Marca la clase como un componente embebible en una entidad JPA.
@Embeddable
// Mapea estos atributos a columnas con otro nombre en la tabla.
// Alguno de estos atributos no se pueden actualizar o no pueden ser nulos.
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
    @AttributeOverride(name = "lastLoginAt", column = @Column(name = "last_login_at"))
})
public class Audit {
  // Sirve para marcar la fecha de creación del registro.
  // Instant es una clase que representa fecha y hora en UTC.
  private Instant createdAt;
  // Sirve para marcar la fecha de la última actualización del registro.
  // Instant es una clase que representa fecha y hora en UTC.
  private Instant updatedAt;
  // Sirve para marcar la fecha del último inicio de sesión.
  // Instant es una clase que representa fecha y hora en UTC.
  private Instant lastLoginAt;

  // Método que se ejecuta antes de persistir la entidad.
  @PrePersist
  public void prePersist() {
    // Asigna la fecha y hora actual a createdAt y updatedAt.
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  // Método que se ejecuta antes de actualizar la entidad.
  @PreUpdate
  public void preUpdate() {
    // Asigna la fecha y hora actual a updatedAt.
    this.updatedAt = Instant.now();
  }
}
