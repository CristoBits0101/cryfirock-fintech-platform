package com.cryfirock.oauth2.provider.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.oauth2.provider.service.IUserValidationService;

/**
 * 1. Controlador REST para validaciones de usuarios en OAuth2.
 * 2. Permite solicitudes CORS desde cualquier origen.
 * 3. Mapea las solicitudes a /api/oauth2/validate.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestController
@CrossOrigin
@RequestMapping("/api/oauth2/validate")
public class UserValidationController {
    /**
     * Servicio para validaciones de usuarios.
     */
    private final IUserValidationService userValidationService;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param userValidationService Servicio de validación de usuarios.
     */
    public UserValidationController(IUserValidationService userValidationService) {
        this.userValidationService = userValidationService;
    }

    /**
     * 1. Valida si un email ya está registrado en el sistema.
     * 2. Retorna la disponibilidad del email.
     *
     * @param request Mapa con el email a validar.
     * @return ResponseEntity con la disponibilidad del email.
     */
    @PostMapping("/email")
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
