package com.cryfirock.oauth2.provider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 1. Interfaz para el cliente Feign que se comunica con el microservicio de usuarios.
 * 2. Contrato de implementación para las clases que la implementen.
 * 3. Obliga a cumplir la convención de nombres y responsabilidades definidas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@FeignClient(name = "msvc-users")
public interface UserFeignClient {
    /**
     * Llamada al endpoint para verificar si un correo electrónico ya existe.
     *
     * @param email El correo electrónico a verificar.
     * @param username El nombre de usuario a verificar.
     * @param phoneNumber El número de teléfono a verificar.
     * @return true si el dato ya existe y false en caso contrario.
     */
    @GetMapping("/api/validations/exists")
    ResponseEntity<Boolean> checkDataExists(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phoneNumber);
}
