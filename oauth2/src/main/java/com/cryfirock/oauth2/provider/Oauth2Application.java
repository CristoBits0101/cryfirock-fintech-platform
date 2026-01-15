package com.cryfirock.oauth2.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 1. Clase principal de la aplicación OAuth2.
 * 2. Habilita clientes Feign para llamadas a servicios externos.
 * 3. Configura y arranca el contexto de Spring Boot.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@EnableFeignClients
@SpringBootApplication
public class Oauth2Application {
    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }
}
