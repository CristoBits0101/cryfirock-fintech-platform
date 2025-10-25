package com.creativadigital360.auth.validation;

import com.creativadigital360.auth.service.IUserQueryService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * ==============================================================================================
 * Paso 8.1: Clase validadora para email
 * ==============================================================================================
 */
@Component
public class ExistsByEmailValidationImpl implements ConstraintValidator<IExistsByEmail, String> {

    /**
     * ==========================================================================================
     * Paso 8.2: Servicio de usuarios
     * ==========================================================================================
     */
    // Referencia a IUserQueryService para consultar si un email existe
    private final IUserQueryService userQueryService;

    /**
     * ==========================================================================================
     * Paso 8.3: Constructores
     * ==========================================================================================
     */
    public ExistsByEmailValidationImpl() {
        this.userQueryService = null;
    }

    // Recibe IUserQueryService desde el contenedor de Spring
    public ExistsByEmailValidationImpl(IUserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * ==========================================================================================
     * Paso 8.4: Lógica de validación
     * ==========================================================================================
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userQueryService == null)
            return true;
        // Retorna true si está vacío o si el correo no existe en la base de datos
        return (value == null || value.trim().isEmpty())
                ? true
                : !userQueryService.existsByEmail(value.trim());
    }

}
