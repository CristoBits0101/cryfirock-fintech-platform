package com.cryfirock.auth.service.validation;

import org.springframework.stereotype.Component;

import com.cryfirock.auth.service.service.IUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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

    // Referencia a IUserService para consultar si un email existe
    private final IUserService userService;

    /**
     * ==========================================================================================
     * Paso 8.3: Constructor con inyección
     * ==========================================================================================
     */

    // Recibe IUserService desde el contenedor de Spring
    public ExistsByEmailValidationImpl(IUserService userService) {
        this.userService = userService;
    }

    /**
     * ==========================================================================================
     * Paso 8.4: Lógica de validación
     * ==========================================================================================
     */

    // Retorna true si está vacío o si el número no existe en la base de datos
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.trim().isEmpty())
                ? true
                : !userService.existsByEmail(value.trim());
    }
}
