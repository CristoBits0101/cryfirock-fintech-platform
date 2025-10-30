package com.cryfirock.auth.validation;

import org.springframework.stereotype.Component;

import com.cryfirock.auth.helper.ValidationHelper;
import com.cryfirock.auth.service.IUserQueryService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
    return ValidationHelper
        .isValidString(
            value,
            userQueryService == null
                ? null
                : userQueryService::existsByEmail);
  }
}
