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
 * 004: Esta clase puede incrustarse en otra y autogenera métodos.
 * @AllArgsConstructor: Genera constructor con todos los atributos.
 * @NoArgsConstructor: Genera constructor sin argumentos.
 * @Data: Genera getters, setters, toString, equals y hashCode.
 * @Embeddable: Indica que puede ser embebida en otras entidades.
 * @AttributeOverrides: Personaliza el mapeo de atributos embebidos.
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
