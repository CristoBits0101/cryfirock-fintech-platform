package com.cryfirock.auth.validation;
import com.cryfirock.auth.service.IUserQueryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
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
        if (userQueryService == null)
            return true;
        return (value == null || value.trim().isEmpty())
                ? true
                : !userQueryService.existsByPhoneNumber(value.trim());
    }
}
