package com.cryfirock.auth.security.handler;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.cryfirock.auth.model.ErrorResponse;
import static com.cryfirock.auth.security.config.TokenJwtConfig.CONTENT_TYPE;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 1. Manejador de errores de acceso denegado.
 * 2. Implementa AccessDeniedHandler de Spring Security.
 * 3. Responde con un JSON de error cuando el acceso es denegado.
 * 4. Retorna código de estado HTTP 403 Forbidden.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 1. ObjectMapper para serializar la respuesta de error a JSON.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 1. Maneja excepciones de acceso denegado.
     * 2. Construye un ErrorResponse con los detalles del error.
     * 3. Escribe la respuesta JSON con estado 403.
     *
     * @param request Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @param accessDeniedException Excepción de acceso denegado.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse error = new ErrorResponse();

        error.setDate(new Date());
        error.setError("Forbidden");
        error.setMessage(accessDeniedException.getMessage());
        error.setStatus(HttpStatus.FORBIDDEN.value());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(CONTENT_TYPE);

        objectMapper.writeValue(response.getWriter(), error);
    }
}
