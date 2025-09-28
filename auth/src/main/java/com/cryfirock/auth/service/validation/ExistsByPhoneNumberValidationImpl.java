package com.cryfirock.auth.service.validation;

import com.cryfirock.auth.service.service.IUserQueryService;

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

    // Referencia a IUserQueryService para consultar si un phone number existe
    private final IUserQueryService userQueryService;

    /**
     * ======================================================================================================
     * Paso 8.3: Constructor con inyección
     * ======================================================================================================
     */

    // Recibe IUserQueryService desde el contenedor de Spring
    public ExistsByPhoneNumberValidationImpl(IUserQueryService userQueryService) {
        this.userQueryService = userQueryService;
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
                : !userQueryService.existsByPhoneNumber(value.trim());
    }
}
