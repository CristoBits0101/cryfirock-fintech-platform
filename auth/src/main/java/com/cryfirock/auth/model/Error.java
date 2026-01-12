package com.cryfirock.auth.model;

import java.util.Date;

import lombok.Data;

/**
 * 1. Clase que representa un error en la aplicación.
 * 2. Genera getters, setters, toString, hashCode y equals.
 */
@Data
public class Error {
  /**
   * 1. Mensaje descriptivo del error.
   * 2. Error específico ocurrido.
   * 3. Código de estado HTTP asociado al error.
   * 4. Fecha y hora en que ocurrió el error.
   */
  private String message;
  private String error;
  private int status;
  private Date date;
}
