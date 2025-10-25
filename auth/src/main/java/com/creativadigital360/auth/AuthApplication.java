package com.creativadigital360.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ==================================================================================================
 * Paso 1.1: Clase principal que arranca la aplicación Spring Boot
 * ==================================================================================================
 */

// Habilita el uso de aspectos con proxies
@EnableAspectJAutoProxy
// Configura el escaneo de componentes y la auto-configuración de Spring Boot
@SpringBootApplication
public class AuthApplication {

    /**
     * =============================================================================================
     * Paso 1.2: Método main que inicia el contexto de Spring
     * =============================================================================================
     */
    public static void main(String[] args) {
        // Ejecuta la aplicación con la configuración actual
        SpringApplication.run(AuthApplication.class, args);
    }

}
