package com.cryfirock.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 1. Principal clase de la aplicación Auth.
 * 2. Anotación @EnableAspectJAutoProxy habilita el soporte para AspectJ.
 * 3. Anotación @SpringBootApplication indica que es una aplicación Spring Boot.
 * 4. Método main inicia la aplicación.
 * 5. Usa SpringApplication.run para lanzar la aplicación con los argumentos proporcionados.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
