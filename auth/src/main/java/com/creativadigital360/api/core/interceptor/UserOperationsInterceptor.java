package com.creativadigital360.api.core.interceptor;

import com.creativadigital360.api.core.security.config.TokenJwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * =============================================================================================================================
 * Paso --.-: Interceptor para operaciones de usuario
 * =============================================================================================================================
 */
@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {

    /**
     * =========================================================================================================================
     * Paso --.-: Atributos
     * =========================================================================================================================
     */
    private static final Logger logger = LoggerFactory.getLogger(UserOperationsInterceptor.class);

    /**
     * =========================================================================================================================
     * Paso --.-: Métodos
     * =========================================================================================================================
     */
    @Override
    public boolean preHandle(
            // Solicitud HTTP que se está procesando
            @NonNull HttpServletRequest request,
            // Respuesta HTTP que se enviará al cliente
            @NonNull HttpServletResponse response,
            // Objeto que maneja la solicitud generalmente un método de controlador
            @NonNull Object handler) throws Exception {

        // Solo intercepta métodos de controladores
        if (!(handler instanceof HandlerMethod))
            return true;

        // Convierte el objeto a HandlerMethod para acceder a metadatos del controlador
        HandlerMethod controller = (HandlerMethod) handler;
        // Obtiene el endpoint de la solicitud
        String endpoint = request.getRequestURI();
        // Obtiene el método HTTP (POST, GET, PUT, DELETE...)
        String method = request.getMethod();

        // Registra la marca de tiempo inicial
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        // Valida el token JWT excepto para el registro público de usuarios
        if (!endpoint.equals("/api/users") || !method.equals("POST")) {
            // Obtiene el encabezado Authorization de la solicitud del JSON Web Token
            String authHeader = request.getHeader("Authorization");

            // Verifica que el encabezado no sea nulo y comience con Bearer
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Unauthorized access attempt to {}", endpoint);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT requerido");
                return false;
            }

            try {
                // Extrae el token del encabezado (remueve Bearer)
                String token = authHeader.substring(7);
                // Parsea y valida el token usando la clave secreta
                Claims claims = Jwts.parser()
                        .verifyWith(TokenJwtConfig.SECRET_KEY)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                // Comprueba la expiración del token
                if (claims.getExpiration().before(new Date())) {
                    logger.warn("Token expired for user {}", claims.getSubject());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    return false;
                }

                // Registra la información del usuario
                request.setAttribute("username", claims.getSubject());

            } catch (JwtException | IOException | IllegalArgumentException e) {
                logger.error("Error validating JWT token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return false;
            }
        }

        // Registra el método HTTP, endpoint y nombre del controlador
        logger.info("Starting operation {} on {} - Controller: {}",
                method, endpoint, controller.getMethod().getName());

        return true;
    }

    @Override
    public void postHandle(
            // Solicitud HTTP que se está procesando
            @NonNull HttpServletRequest request,
            // Respuesta HTTP que se enviará al cliente
            @NonNull HttpServletResponse response,
            // Objeto que maneja la solicitud generalmente un método de controlador
            @NonNull Object handler,
            // Modelo y vista que se devolverá (puede ser null)
            @Nullable ModelAndView modelAndView) throws Exception {

        // Solo intercepta métodos de controladores
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        // Convierte el objeto a HandlerMethod para acceder a metadatos del controlador
        HandlerMethod controller = (HandlerMethod) handler;
        // Obtiene la marca de tiempo al finalizar el procesamiento de la solicitud
        long end = System.currentTimeMillis();
        // Obtiene la marca de tiempo al iniciar el procesamiento de la solicitud
        long start = (long) request.getAttribute("start");
        // Calcula la duración de la operación restando inicio a fin
        long duration = end - start;
        // Obtiene el usuario autenticado (puede ser null)
        String username = (String) request.getAttribute("username");

        // Registra el resumen de la operación
        String logMessage = String.format(
                "Operation %s completed in %d ms - User: %s - Status: %d",
                request.getMethod(),
                duration,
                username != null ? username : "anon",
                response.getStatus());

        // Ajusta el nivel del log según el método HTTP
        switch (request.getMethod()) {
            case "POST", "PUT", "DELETE" -> logger.warn(logMessage);
            default -> logger.info(logMessage);
        }

        // Registra las operaciones especialmente sensibles
        if (response.getStatus() >= 400) {
            logger.error("Error in operation {}: {} - {}",
                    controller.getMethod().getName(),
                    response.getStatus(),
                    request.getRequestURI());
        }
    }
}
