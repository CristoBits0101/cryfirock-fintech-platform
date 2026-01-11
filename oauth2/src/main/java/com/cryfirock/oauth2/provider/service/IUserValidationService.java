package com.cryfirock.oauth2.provider.service;

// Interface para validaciones de usuarios.
// Contrato de implementación para las clases que la implementen.
// Obliga a cumplir la convención de nombres y responsabilidades definidas.
public interface IUserValidationService {
    /**
     * Valida si un email ya está registrado.
     * 
     * @param email el email a validar.
     * @return true si el email ya está registrado y false en caso contrario.
     */
    boolean isEmailAlreadyRegistered(String email);
}
