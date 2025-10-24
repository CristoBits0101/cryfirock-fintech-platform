package com.creativadigital360.api.core.validation;

import com.creativadigital360.api.core.service.IUserQueryService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class ExistsByEmailValidationImpl implements ConstraintValidator<IExistsByEmail, String> {

    private final IUserQueryService userQueryService;

    public ExistsByEmailValidationImpl() {
        this.userQueryService = null;
    }

    public ExistsByEmailValidationImpl(IUserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userQueryService == null) return true;
        return (value == null || value.trim().isEmpty())
                ? true
                : !userQueryService.existsByEmail(value.trim());
    }

}
