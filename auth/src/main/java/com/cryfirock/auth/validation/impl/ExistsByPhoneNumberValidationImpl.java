package com.cryfirock.auth.validation.impl;

import org.springframework.stereotype.Component;

import com.cryfirock.auth.service.api.IUserQueryService;
import com.cryfirock.auth.util.ValidationUtil;
import com.cryfirock.auth.validation.api.IExistsByPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 1. Implementación de validación para verificar existencia de teléfono.
 * 2. Implementa ConstraintValidator con anotación IExistsByPhoneNumber y tipo String.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Component
public class ExistsByPhoneNumberValidationImpl implements ConstraintValidator<IExistsByPhoneNumber, String> {
    // 1. Servicio para consultar usuarios.
    // 2. Inyectado vía constructor.
    private final IUserQueryService userQueryService;

    public ExistsByPhoneNumberValidationImpl() {
        this.userQueryService = null;
    }

    /**
     * 1. Constructor que inyecta el servicio de consulta de usuarios.
     *
     * @param userQueryService Servicio de consulta de usuarios.
     */
    public ExistsByPhoneNumberValidationImpl(IUserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * 1. Método para validar si el número de teléfono ya existe.
     * 2. Usa ValidationUtil para verificar si el número es válido y no existe.
     * 3. Retorna true si el número no existe y false en caso contrario.
     *
     * @param value Número de teléfono a validar.
     * @param context Contexto de la validación.
     * @return boolean Resultado de la validación.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ValidationUtil
                .isValidString(
                        value,
                        userQueryService == null
                                ? null
                                : userQueryService::existsByPhoneNumber);
    }
}
