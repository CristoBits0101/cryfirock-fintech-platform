package com.cryfirock.oauth2.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.oauth2.provider.dto.ClientRegistrationRequest;
import com.cryfirock.oauth2.provider.service.IUserValidationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/oauth2/clients")
public class ClientRegistrationController {
    @Autowired
    private IUserValidationService userValidationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody ClientRegistrationRequest request) {
        if (userValidationService.isEmailAlreadyRegistered(request.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "EMAIL_ALREADY_EXISTS");
            error.put("message", "El email " + request.getEmail() + " ya está registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cliente registrado exitosamente");
        response.put("email", request.getEmail());
        response.put("clientName", request.getClientName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/validate-email")
    public ResponseEntity<?> validateEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "INVALID_EMAIL");
            error.put("message", "El email no puede estar vacío");
            return ResponseEntity.badRequest().body(error);
        }

        boolean exists = userValidationService.isEmailAlreadyRegistered(email);

        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("available", !exists);
        response.put("message", exists ? "Email no disponible" : "Email disponible");

        return ResponseEntity.ok(response);
    }
}
