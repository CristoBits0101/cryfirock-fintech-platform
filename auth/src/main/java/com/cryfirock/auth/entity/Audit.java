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
// Sobrescribe las columnas para mapear los atributos a las columnas de la tabla.
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
