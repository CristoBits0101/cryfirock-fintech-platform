package com.creativadigital360.api.core.security.config;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenJwtConfig {

    public static final String CONTENT_TYPE = "application/json";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String PREFIX_TOKEN = "Bearer";

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

}
