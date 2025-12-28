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

  /**
   * Atributos de auditoría.
   * Guarda el tiempo exacto en nanosegundos.
   * UTC por defecto es siempre igual en todo el mundo.
   * TIMESTAMP se mapea perfectamente a Instant.
   */
  private Instant createdAt;
  private Instant updatedAt;
  private Instant lastLoginAt;

  // Se ejecuta antes de persistir la entidad.
  @PrePersist
  public void prePersist() {
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  // Se ejecuta antes de actualizar la entidad.
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = Instant.now();
  }

}
