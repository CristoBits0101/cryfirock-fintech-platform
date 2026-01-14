package com.cryfirock.oauth2.provider.model;

import java.util.Date;

/**
 * 1. Record que representa un error en la aplicación.
 * 2. Utilizado para respuestas de error estandarizadas.
 * 3. Inmutable por diseño al ser un record de Java.
 *
 * @param message Mensaje descriptivo del error.
 * @param error Tipo de error.
 * @param status Código de estado HTTP.
 * @param date Fecha y hora del error.
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public record Error(String message, String error, int status, Date date) {
}
