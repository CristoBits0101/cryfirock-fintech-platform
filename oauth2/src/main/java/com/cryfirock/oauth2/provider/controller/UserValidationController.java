package com.cryfirock.oauth2.provider.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.oauth2.provider.service.contract.IUserValidationService;

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
@RestController @CrossOrigin @RequestMapping("/api/oauth2/validate")
public class UserValidationController {
    // Servicio para validaciones de usuarios.
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
     * @param email Email a validar.
     * @return ResponseEntity con la disponibilidad del email.
     */
    @GetMapping("/email")
    public ResponseEntity<Map<String, Boolean>> validateEmail(@RequestParam String email) {
        return ResponseEntity
                .ok(Map.of("emailExists", userValidationService.isEmailAlreadyRegistered(email)));
    }
}
