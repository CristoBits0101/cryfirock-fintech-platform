package com.cryfirock.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByPhoneNumberValidationImpl.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IExistsByPhoneNumber {
  String message() default "Phone number already exists.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
