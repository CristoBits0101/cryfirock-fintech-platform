package com.cryfirock.auth.service.validations;

import com.cryfirock.auth.service.service.IUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ==============================================================================================
 * Paso 8.1: Clase validadora para username
 * ==============================================================================================
 */
public class ExistsByUsernameValidationImpl implements ConstraintValidator<IExistsByUsername, String> {

    /**
     * ==========================================================================================
     * Paso 8.2: Servicio de usuarios
     * ==========================================================================================
     */
    // Referencia a IUserService para consultar si un username existe
    private final IUserService userService;

    /**
     * ==========================================================================================
     * Paso 8.3: Constructor con inyección
     * ==========================================================================================
     */
    // Recibe IUserService desde el contenedor de Spring
    public ExistsByUsernameValidationImpl(IUserService userService) {
        this.userService = userService;
    }

    /**
     * ==========================================================================================
     * Paso 8.4: Lógica de validación
     * ==========================================================================================
     */
    // Retorna true si está vacío o si el username no existe en la base de datos
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.trim().isEmpty())
                ? true
                : !userService.existsByUsername(value.trim());
    }
}
