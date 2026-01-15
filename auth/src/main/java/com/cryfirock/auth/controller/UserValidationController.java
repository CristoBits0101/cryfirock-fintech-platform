package com.cryfirock.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.auth.service.contract.IUserQueryService;
import com.cryfirock.auth.util.ValidationUtil;

/**
 * 1. Controlador REST para validaciones de usuarios.
 * 2. Permite solicitudes CORS desde cualquier origen.
 * 3. Mapea las solicitudes a /api/validations.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestController @CrossOrigin @RequestMapping("/api/validations")
public class UserValidationController {
    /**
     * 1. Inyección automática del servicio.
     * 2. Servicio para consultas relacionadas con usuarios.
     */
    @Autowired
    private IUserQueryService userQueryService;

    /**
     * 1. Verifica si un campo de usuario ya está registrado.
     * 2. Mapea las solicitudes GET a /exists con parámetros opcionales.
     * 3. Solo debe proporcionarse uno de los tres parámetros.
     * 4. Retorna true si el valor existe y false en caso contrario.
     * 5. Retorna HTTP 400 si se proporcionan múltiples parámetros o ninguno.
     *
     * {@code GET /exists?email=user@example.com}
     * {@code GET /exists?username=johndoe}
     * {@code GET /exists?phoneNumber=+34612345678}
     *
     * @param email Correo electrónico a verificar (opcional).
     * @param username Nombre de usuario a verificar (opcional).
     * @param phoneNumber Número de teléfono a verificar (opcional).
     * @return ResponseEntity<Boolean> indicando si el valor existe.
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkDataExists(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phoneNumber) {
        return ValidationUtil.findFirstNonBlankIndex(email, username, phoneNumber)
                .map(index -> switch (index) {
                case 1 -> userQueryService.existsByEmail(email);
                case 2 -> userQueryService.existsByUsername(username);
                case 3 -> userQueryService.existsByPhoneNumber(phoneNumber);
                default -> false;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
