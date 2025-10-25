package com.creativadigital360.auth.validation;

import com.creativadigital360.auth.service.IUserQueryService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * ==========================================================================================================
 * Paso 8.1: Clase validadora para phone number
 * ==========================================================================================================
 */
@Component
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
     * Paso 8.3: Constructores
     * ======================================================================================================
     */
    public ExistsByPhoneNumberValidationImpl() {
        this.userQueryService = null;
    }

    // Recibe IUserQueryService desde el contenedor de Spring
    public ExistsByPhoneNumberValidationImpl(IUserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * ======================================================================================================
     * Paso 8.4: Lógica de validación
     * ======================================================================================================
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userQueryService == null)
            return true;
        // Retorna true si está vacío o si el phone number no existe en la base de datos
        return (value == null || value.trim().isEmpty())
                ? true
                : !userQueryService.existsByPhoneNumber(value.trim());
    }
}
