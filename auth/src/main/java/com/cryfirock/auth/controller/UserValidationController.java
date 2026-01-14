package com.cryfirock.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.auth.service.contract.IUserQueryService;

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
@RestController
@CrossOrigin
@RequestMapping("/api/validations")
public class UserValidationController {
    /**
     * 1. Inyección automática del servicio.
     * 2. Servicio para consultas relacionadas con usuarios.
     */
    @Autowired
    private IUserQueryService userQueryService;

    /**
     * 1. Verifica si un correo electrónico ya está registrado.
     * 2. Mapea las solicitudes GET a /exists/email/{email}.
     * 3. Devuelve un ResponseEntity con un valor booleano.
     * 4. @PathVariable para capturar el correo electrónico de la URL.
     * 5. Llama al servicio para verificar la existencia del correo electrónico.
     * 6. Retorna true si el correo existe y false en caso contrario.
     * 7. ResponseEntity permite personalizar el JSON y el estado HTTP.
     * 
     * @param email El correo electrónico a verificar.
     * @return ResponseEntity<Boolean> indicando si el correo existe.
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userQueryService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * 1. Verifica si un nombre de usuario ya está registrado.
     * 2. Mapea las solicitudes GET a /exists/username/{username}.
     * 3. Devuelve un ResponseEntity con un valor booleano.
     * 4. @PathVariable para capturar el nombre de usuario de la URL.
     * 5. Llama al servicio para verificar la existencia del nombre de usuario.
     * 6. Retorna true si el nombre de usuario existe y false en caso contrario.
     * 7. ResponseEntity permite personalizar el JSON y el estado HTTP.
     * 
     * @param username El nombre de usuario a verificar.
     * @return ResponseEntity<Boolean> indicando si el nombre de usuario existe.
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
        boolean exists = userQueryService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    /**
     * 1. Verifica si un número de teléfono ya está registrado.
     * 2. Mapea las solicitudes GET a /exists/phone/{phoneNumber}.
     * 3. Devuelve un ResponseEntity con un valor booleano.
     * 4. @PathVariable para capturar el número de teléfono de la URL.
     * 5. Llama al servicio para verificar la existencia del número de teléfono.
     * 6. Retorna true si el número de teléfono existe y false en caso contrario.
     * 7. ResponseEntity permite personalizar el JSON y el estado HTTP.
     * 
     * @param phoneNumber El número de teléfono a verificar.
     * @return ResponseEntity<Boolean> indicando si el número de teléfono existe.
     */
    @GetMapping("/exists/phone/{phoneNumber}")
    public ResponseEntity<Boolean> checkPhoneNumberExists(@PathVariable String phoneNumber) {
        boolean exists = userQueryService.existsByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(exists);
    }
}
