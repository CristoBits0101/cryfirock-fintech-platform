package com.cryfirock.auth.service.security.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;

/**
 * ==============================================================================================================================
 * Paso 21.1: Configuración de JWT (Valores constantes)
 * ==============================================================================================================================
 */

// Indica que esta clase es una configuración de Spring
@Configuration
public class TokenJwtConfig {

    /**
     * ==============================================================================================================================
     * Paso 21.2: Atributos
     * ==============================================================================================================================
     */

    // Tipo de contenido para las respuestas HTTP
    public static final String CONTENT_TYPE = "application/json";

    // Duración del token en milisegundos (1 hora)
    public static final String HEADER_AUTHORIZATION = "Authorization";

    // Prefijo del token en el encabezado HTTP
    public static final String PREFIX_TOKEN = "Bearer";

    // Clave secreta para firmar y verificar tokens JWT
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

}
