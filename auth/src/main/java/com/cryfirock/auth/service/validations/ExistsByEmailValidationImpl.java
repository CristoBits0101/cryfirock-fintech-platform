package com.cryfirock.auth.service.validations;

import org.springframework.stereotype.Component;

import com.cryfirock.auth.service.services.IUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByEmailValidationImpl implements ConstraintValidator<IExistsByEmail, String> {

    private final IUserService userService;

    public ExistsByEmailValidationImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Permite null/vacío: usa @NotBlank para eso
        if (value == null || value.trim().isEmpty()) return true;

        String email = value.trim();
        // true = válido cuando NO existe
        return !userService.existsByEmail(email);
    }
}
