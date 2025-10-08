package com.cryfirock.auth.service.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cryfirock.auth.service.security.config.TokenJwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ==============================================================================================================================
 * Paso --.-: Interceptor para operaciones de usuario
 * ==============================================================================================================================
 */

@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {

    /**
     * ==========================================================================================================================
     * Paso --.-: Atributos
     * ==========================================================================================================================
     */

    private static final Logger logger = LoggerFactory.getLogger(UserOperationsInterceptor.class);

    private static final Pattern NUMERIC_USER_PATH = Pattern.compile("^/api/users/\\d+$");

    /**
     * ==========================================================================================================================
     * Paso --.-: Métodos
     * ==========================================================================================================================
     */

    /**
     * @param request
     * @param response
     * @param handler
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

        // Convierte el objeto
        HandlerMethod controller = (HandlerMethod) handler;

        // Obtiene el endpoint de la solicitud
        String endpoint = request.getRequestURI();
        // Obtiene el método HTTP (POST, GET, PUT, DELETE...)
        String method = request.getMethod();

        // Obtiene la marca de tiempo al iniciar el procesamiento de la solicitud
        long start = System.currentTimeMillis();
        // Guarda la marca de tiempo en la solicitud para usarla después
        request.setAttribute("start", start);

        // Valida el token JWT solo para rutas protegidas
        if (requiresAuthentication(method, endpoint)) {
            // Obtiene el encabezado Authorization de la solicitud del JSON Web Token
            String authHeader = request.getHeader("Authorization");

            // Verifica que el encabezado no sea nulo y comience con Bearer
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // Respuesta 401 si el token no está presente o es inválido
                logger.warn("Unauthorized access attempt to {}", endpoint);
                // Enviar error 401 (no autorizado) si el token no está presente o es inválido
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT requerido");
                // Detener el procesamiento de la solicitud
                return false;
            }

            try {
                // Extrae el token del encabezado (remueve Bearer)
                String token = authHeader.substring(7);
                // Parsea y valida el token usando la clave secreta
                Claims claims = Jwts.parser()
                        // Usa la clave secreta para verificar la firma del token
                        .verifyWith(TokenJwtConfig.SECRET_KEY)
                        // Parsea el token y obtiene las reclamaciones (claims)
                        .build()
                        // Parsea el token firmado y obtiene las reclamaciones (claims)
                        .parseSignedClaims(token)
                        // Obtiene las reclamaciones (claims) del token
                        .getPayload();

                // Comprueba la expiración del token
                if (claims.getExpiration().before(new Date())) {
                    // Token expirado
                    logger.warn("Token expired for user {}", claims.getSubject());
                    // Enviar error 401 (no autorizado) si el token ha expirado
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    // Detener el procesamiento de la solicitud
                    return false;
                }

                // Registra la información del usuario
                request.setAttribute("username", claims.getSubject());

            } catch (JwtException | IOException | IllegalArgumentException e) {
                // Token inválido
                logger.error("Error validating JWT token: {}", e.getMessage());
                // Enviar error 401 (no autorizado) si el token es inválido
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                // Detener el procesamiento de la solicitud
                return false;
            }
        }

        // Registra el método HTTP, endpoint y nombre del controlador
        logger.info("Starting operation {} on {} - Controller: {}",
                // Método HTTP (POST, GET, PUT, DELETE...)
                method,
                // Endpoint de la solicitud
                endpoint,
                // Nombre del método del controlador que maneja la solicitud
                controller.getMethod().getName());

        return true;
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
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

        // Convierte el objeto
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
                // Mensaje de log con detalles de la operación
                "Operation %s completed in %d ms - User: %s - Status: %d",
                // Método HTTP de la solicitud
                request.getMethod(),
                // Duración en milisegundos
                duration,
                // Nombre del usuario o anon si es nulo
                username != null ? username : "anon",
                // Código de estado HTTP de la respuesta
                response.getStatus());

        // Ajusta el nivel del log según el método HTTP
        switch (request.getMethod()) {
            // Operaciones que modifican datos
            case "POST", "PUT", "DELETE" -> logger.warn(logMessage);
            // Operaciones que no modifican datos
            default -> logger.info(logMessage);
        }

        // Registra las operaciones especialmente sensibles
        if (response.getStatus() >= 400) {
            // Registra errores y advertencias
            logger.error("Error in operation {}: {} - {}",
                    // Nombre del método del controlador que maneja la solicitud
                    controller.getMethod().getName(),
                    // Código de estado HTTP de la respuesta
                    response.getStatus(),
                    // Endpoint de la solicitud
                    request.getRequestURI());
        }
    }

    /**
     * Determina si una ruta requiere autenticación mediante JWT.
     *
     * @param method   Método HTTP de la solicitud.
     * @param endpoint URI solicitado.
     * @return {@code true} si la ruta debe estar protegida.
     */
    private boolean requiresAuthentication(String method, String endpoint) {
        // Permite el registro público de usuarios
        if ("POST".equals(method) && "/api/users".equals(endpoint)) {
            return false;
        }

        // Permite accesos públicos GET/PUT a rutas numéricas específicas
        if (("GET".equals(method) || "PUT".equals(method))
                && NUMERIC_USER_PATH.matcher(endpoint).matches()) {
            return false;
        }

        // El resto de rutas permanecen protegidas
        return true;
    }
}