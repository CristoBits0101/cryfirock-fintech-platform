package com.cryfirock.auth.util;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class ValidationUtils {
  public ResponseEntity<?> reportIncorrectFields(BindingResult result) {
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
