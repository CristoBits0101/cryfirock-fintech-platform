package com.cryfirock.auth.service.interceptors;

import com.cryfirock.auth.service.security.config.TokenJwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {

    /**
     * Atributos
     */
    private static final Logger logger = LoggerFactory.getLogger(UserOperationsInterceptor.class);

    /**
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {

        // Solo intercepta métodos de controladores
        if (!(handler instanceof HandlerMethod))
            return true;

        // Convierte el objeto
        HandlerMethod controller = (HandlerMethod) handler;

        // Obtiene la ruta y el método
        String endpoint = request.getRequestURI();
        String method = request.getMethod();

        // Medición de tiempo
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        // Valida el token JWT para rutas protegidas
        if (!endpoint.equals("/api/users") || !method.equals("POST")) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Unauthorized access attempt to {}", endpoint);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT requerido");
                return false;
            }

            try {
                String token = authHeader.substring(7);
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

            } catch (Exception e) {
                logger.error("Error validating JWT token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return false;
            }
        }

        logger.info("Starting operation {} on {} - Controller: {}",
                method,
                endpoint,
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
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        // Solo intercepta métodos de controladores
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        // Convierte el objeto
        HandlerMethod controller = (HandlerMethod) handler;

        // Obtiene la marca de tiempo al finalizar el procesamiento de la solicitud
        long end = System.currentTimeMillis();
        long start = (long) request.getAttribute("start");

        // Calcula la duración de la operación restando inicio a fin
        long duration = end - start;

        // Obtiene el usuario autenticado (puede ser null)
        String username = (String) request.getAttribute("username");

        // Registra el método HTTP, tiempo de ejecución, usuario (o 'anon') y código de estado
        String logMessage = String.format(
                "Operation %s completed in %d ms - User: %s - Status: %d",
                request.getMethod(),
                duration,
                username != null ? username : "anon",
                response.getStatus());

        // Ajusta el nivel del log según el método HTTP
        switch (request.getMethod()) {
            case "POST":
            case "PUT":
            case "DELETE":
                logger.warn(logMessage);
                break;
            default:
                logger.info(logMessage);
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