package com.cryfirock.auth.model;

/**
 * 1. Enumera los posibles estados de una cuenta de usuario.
 * 2. Proporciona una forma segura de manejar los estados de la cuenta.
 */
public enum AccountStatus {
  /**
   * 1. La cuenta está pendiente de activación.
   * 2. La cuenta está activa y en buen estado.
   * 3. La cuenta está suspendida temporalmente.
   * 4. La cuenta está baneada permanentemente.
   * 5. La cuenta está cerrada por el usuario.
   */
  PENDING,
  ACTIVE,
  SUSPENDED,
  BANNED,
  CLOSED
}
