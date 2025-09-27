package com.cryfirock.auth.service.validations;

import com.cryfirock.auth.service.service.IUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ==========================================================================================================
 * Paso 8.1: Clase validadora para phone number
 * ==========================================================================================================
 */
public class ExistsByPhoneNumberValidationImpl implements ConstraintValidator<IExistsByPhoneNumber, String> {

    /**
     * ======================================================================================================
     * Paso 8.2: Servicio de usuarios
     * ======================================================================================================
     */

    // Referencia a IUserService para consultar si un phone number existe
    private final IUserService userService;

    /**
     * ======================================================================================================
     * Paso 8.3: Constructor con inyección
     * ======================================================================================================
     */

    // Recibe IUserService desde el contenedor de Spring
    public ExistsByPhoneNumberValidationImpl(IUserService userService) {
        this.userService = userService;
    }

    /**
     * ======================================================================================================
     * Paso 8.4: Lógica de validación
     * ======================================================================================================
     */

    // Retorna true si está vacío o si el phone number no existe en la base de datos
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.trim().isEmpty())
                ? true
                : !userService.existsByPhoneNumber(value.trim());
    }
}
