package com.cryfirock.auth.security.config;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;

/**
 * ======================================================================================
 * Paso 18.7: Constantes de configuración para la emisión y validación de JWT
 * ======================================================================================
 */
@Configuration
public class TokenJwtConfig {

    // Tipo de contenido utilizado en las respuestas JSON
    public static final String CONTENT_TYPE = "application/json";

    // Nombre del encabezado HTTP donde se envía el token
    public static final String HEADER_AUTHORIZATION = "Authorization";

    // Prefijo estándar para tokens Bearer
    public static final String PREFIX_TOKEN = "Bearer";

    // Clave secreta para firmar y validar los JWT (HS256)
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

}
