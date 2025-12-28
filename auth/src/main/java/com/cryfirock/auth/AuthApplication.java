package com.cryfirock.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 001: Configuración principal de la aplicación de autenticación.
 * 
 * @EnableAspectJAutoProxy: Habilita proxies AOP para interceptar métodos.
 * @SpringBootApplication: Marca esta clase como aplicación Spring Boot.
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class AuthApplication {

  /**
   * Punto de entrada principal de la aplicación Spring Boot.
   * 
   * @param args: Argumentos de línea de comandos.
   */
  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }

}
