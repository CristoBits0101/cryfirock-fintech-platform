package com.cryfirock.auth.exception;

/**
 * 1. Excepción personalizada para indicar que un usuario no fue encontrado.
 * 2. Extiende RuntimeException para ser una excepción no verificada.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
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
