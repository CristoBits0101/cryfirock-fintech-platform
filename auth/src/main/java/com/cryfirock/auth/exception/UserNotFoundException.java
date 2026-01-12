package com.cryfirock.auth.exception;

/**
 * 1. Excepción personalizada para indicar que un usuario no fue encontrado.
 * 2. Extiende RuntimeException para ser una excepción no verificada.
 */
public class UserNotFoundException extends RuntimeException {
  /**
   * 1. Constructor que acepta un mensaje de error.
   * 
   * @param message Mensaje de error detallando la excepción.
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}
