package com.cryfirock.auth.validation;

import com.cryfirock.auth.service.IUserQueryService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * ==========================================================================================================
 * Paso 8.1: Clase validadora para username
 * ==========================================================================================================
 */
@Component
public class ExistsByUsernameValidationImpl implements ConstraintValidator<IExistsByUsername, String> {

    /**
     * ======================================================================================================
     * Paso 8.2: Servicio de usuarios
     * ======================================================================================================
     */
    // Referencia a IUserQueryService para consultar si un username existe
    private final IUserQueryService userQueryService;

    /**
     * ======================================================================================================
     * Paso 8.3: Constructores
     * ======================================================================================================
     */
    public ExistsByUsernameValidationImpl() {
        this.userQueryService = null;
    }

    // Recibe IUserQueryService desde el contenedor de Spring
    public ExistsByUsernameValidationImpl(IUserQueryService userQueryService) {
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
        // Retorna true si está vacío o si el username no existe en la base de datos
        return (value == null || value.trim().isEmpty())
                ? true
                : !userQueryService.existsByUsername(value.trim());
    }

}
