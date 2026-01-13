package com.cryfirock.auth.security.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;

/**
 * 1. Configuración de tokens JWT.
 * 2. Define constantes para la autenticación basada en tokens.
 * 3. Genera una clave secreta para firmar y verificar tokens.
 */
@Configuration
public class TokenJwtConfig {
  /**
   * 1. Tipo de contenido para respuestas JSON.
   * 2. Encabezado HTTP para el token de autorización.
   * 3. Prefijo del token Bearer.
   * 4. Clave secreta para firmar tokens JWT con HS256.
   */
  public static final String CONTENT_TYPE = "application/json";
  public static final String HEADER_AUTHORIZATION = "Authorization";
  public static final String PREFIX_TOKEN = "Bearer";
  public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
}
