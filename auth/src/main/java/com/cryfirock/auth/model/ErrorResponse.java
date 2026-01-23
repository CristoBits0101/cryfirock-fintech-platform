package com.cryfirock.auth.model;

import java.util.Date;

import lombok.Data;

/**
 * 1. Clase que representa un error en la aplicación.
 * 2. @Data:
 *  - Genera Getters
 *  - Genera Setters
 *  - Genera toString
 *  - Genera hashCode
 *  - Genera equals
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Data
public class ErrorResponse {
    // 1. Mensaje descriptivo del error.
    // 2. Error específico ocurrido.
    // 3. Código de estado HTTP asociado al error.
    // 4. Fecha y hora en que ocurrió el error.
    private String customMessage;
    private String errorMessage;
    private int statusCode;
    private Date errorDate;
}
