package com.cryfirock.accounts.exception;

/**
 * 1. Excepción lanzada cuando una cuenta no es encontrada.
 * 2. Extiende RuntimeException para manejo no verificado.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public class AccountNotFoundException extends RuntimeException {
    /**
     * 1. Constructor con mensaje personalizado.
     * 2. Permite especificar el motivo del error.
     *
     * @param message Mensaje de error.
     */
    public AccountNotFoundException(String message) {
        super(message);
    }

    /**
     * 1. Constructor con ID de cuenta no encontrada.
     * 2. Genera mensaje automático con el ID.
     *
     * @param id Identificador de la cuenta no encontrada.
     */
    public AccountNotFoundException(Long id) {
        super("Cuenta con ID " + id + " no encontrada");
    }
}
