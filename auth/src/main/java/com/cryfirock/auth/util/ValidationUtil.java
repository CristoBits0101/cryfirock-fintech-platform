package com.cryfirock.auth.util;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class ValidationUtil {
  private ValidationUtil() {
  }

  public static boolean isValidString(String value, Predicate<String> exists) {
    return !isBlankOrNull(value) && exists != null && !exists.test(value.trim());
  }

  public static boolean isBlankOrNull(String value) {
    return value == null || value.isBlank();
  }

  public static ResponseEntity<?> reportIncorrectFields(BindingResult result) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(result
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage)));
  }
}
