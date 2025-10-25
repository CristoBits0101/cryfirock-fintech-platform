package com.cryfirock.auth.validation;

import org.springframework.stereotype.Component;

import com.cryfirock.auth.service.IUserQueryService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByUsernameValidationImpl implements ConstraintValidator<IExistsByUsername, String> {
    private final IUserQueryService userQueryService;

    public ExistsByUsernameValidationImpl() {
        this.userQueryService = null;
    }

    public ExistsByUsernameValidationImpl(IUserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userQueryService == null) return true;
        return (value == null || value.trim().isEmpty())
                ? true
                : !userQueryService.existsByUsername(value.trim());
    }
}
