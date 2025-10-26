package com.cryfirock.auth.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ValidationUtils {
  public ResponseEntity<?> reportIncorrectFields(BindingResult result) {
    Map<String, String> requestErrors = new HashMap<>();

    result
        .getFieldErrors()
        .forEach(err -> {
          requestErrors.put(err.getField(), err.getDefaultMessage());
        });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(requestErrors);
  }
}
