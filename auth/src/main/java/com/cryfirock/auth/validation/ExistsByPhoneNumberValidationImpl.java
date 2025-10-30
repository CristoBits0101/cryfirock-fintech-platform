package com.cryfirock.auth.validation;

import org.springframework.stereotype.Component;

import com.cryfirock.auth.service.IUserQueryService;
import com.cryfirock.auth.util.ValidationUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByPhoneNumberValidationImpl implements ConstraintValidator<IExistsByPhoneNumber, String> {
  private final IUserQueryService userQueryService;

  public ExistsByPhoneNumberValidationImpl() {
    this.userQueryService = null;
  }

  public ExistsByPhoneNumberValidationImpl(IUserQueryService userQueryService) {
    this.userQueryService = userQueryService;
  }

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
