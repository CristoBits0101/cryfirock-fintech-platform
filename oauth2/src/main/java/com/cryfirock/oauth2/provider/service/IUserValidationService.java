package com.cryfirock.oauth2.provider.service;

/**
 * 1. Interface para validaciones de usuarios.
 * 2. Contrato de implementación para las clases que la implementen.
 * 3. Obliga a cumplir la convención de nombres y responsabilidades definidas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public interface IUserValidationService {
    /**
     * Valida si un email ya está registrado.
     *
     * @param email El email a validar.
     * @return true si el email ya está registrado y false en caso contrario.
     */
    boolean isEmailAlreadyRegistered(String email);
}
