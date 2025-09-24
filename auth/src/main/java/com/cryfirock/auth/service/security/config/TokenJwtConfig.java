package com.cryfirock.msvc.users.msvc_users.security.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;

// It is used to reuse properties
@Configuration
public class TokenJwtConfig {

    /**
     * Attributes
     */
    public static final String CONTENT_TYPE = "application/json";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String PREFIX_TOKEN = "Bearer";

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

}
