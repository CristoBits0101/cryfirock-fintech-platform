package com.creativadigital360.api.core.security.handler;

import static com.creativadigital360.api.core.security.config.TokenJwtConfig.CONTENT_TYPE;

import com.creativadigital360.api.core.model.Error;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        Error error = new Error();

        error.setDate(new Date());
        error.setError("Unauthorized");
        error.setMessage(authException.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(CONTENT_TYPE);
        objectMapper.writeValue(response.getWriter(), error);
    }
    
}
