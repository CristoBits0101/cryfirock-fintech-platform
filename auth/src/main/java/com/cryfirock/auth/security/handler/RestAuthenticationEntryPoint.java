package com.cryfirock.auth.security.handler;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.cryfirock.auth.model.ErrorResponse;
import static com.cryfirock.auth.security.config.TokenJwtConfig.CONTENT_TYPE;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 1. Punto de entrada para errores de autenticación.
 * 2. Implementa AuthenticationEntryPoint de Spring Security.
 * 3. Responde con un JSON de error cuando la autenticación falla.
 * 4. Retorna código de estado HTTP 401 Unauthorized.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 1. ObjectMapper para serializar la respuesta de error a JSON.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 1. Inicia el proceso de autenticación cuando falla.
     * 2. Construye un ErrorResponse con los detalles del error.
     * 3. Escribe la respuesta JSON con estado 401.
     *
     * @param request Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @param authException Excepción de autenticación.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        ErrorResponse error = new ErrorResponse();

        error.setDate(new Date());
        error.setError("Unauthorized");
        error.setMessage(authException.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(CONTENT_TYPE);

        objectMapper.writeValue(response.getWriter(), error);
    }
}
