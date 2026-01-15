package com.cryfirock.oauth2.provider.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 1. Clase que representa un error en la aplicación.
 * 2. Utilizado para respuestas de error estandarizadas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@AllArgsConstructor @Getter @Setter
public class Error {
    // Mensaje descriptivo del error.
    private String message;

    // Tipo de error ocurrido.
    private String error;

    // Código de estado HTTP asociado al error.
    private int status;

    // Fecha y hora en que ocurrió el error.
    private Date date;
}
