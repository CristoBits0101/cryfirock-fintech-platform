package com.cryfirock.auth.validation;

import org.springframework.stereotype.Component;

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
    if (userQueryService == null)
      return true;
    return (userQueryService == null || value == null || value.trim().isEmpty())
        ? true
        : !userQueryService.existsByEmail(value.trim());
  }
}
