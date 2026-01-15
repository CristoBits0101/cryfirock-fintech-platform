package com.cryfirock.oauth2.provider.model;

import java.util.Date;

/**
 * 1. Clase que representa un error en la aplicación.
 * 2. Utilizado para respuestas de error estandarizadas.
 * 3. Inmutable por diseño con campos finales.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public class Error {
    private final String message;
    private final String error;
    private final int status;
    private final Date date;

    public Error(String message, String error, int status, Date date) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }
}
