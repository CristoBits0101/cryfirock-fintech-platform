package com.cryfirock.auth.service.interceptors;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.security.config.TokenJwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.springframework.stereotype.Component;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {

    /**
     * Attributes
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

        // Only intercept controller methods
        if (!(handler instanceof HandlerMethod))
            return true;

        // Parse object
        HandlerMethod controller = (HandlerMethod) handler;

        // Get the route and method
        String endpoint = request.getRequestURI();
        String method = request.getMethod();

        // Time measurement
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        // Validate JWT token for protected routes
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

                // Check token expiration
                if (claims.getExpiration().before(new Date())) {
                    logger.warn("Token expired for user {}", claims.getSubject());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    return false;
                }

                // Record user information
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

        // Only intercept controller methods
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        // Parse object
        HandlerMethod controller = (HandlerMethod) handler;

        // Gets the current timestamp at the end of request processing
        long end = System.currentTimeMillis();
        long start = (long) request.getAttribute("start");

        // Calculate the operation duration by subtracting start from end
        long duration = end - start;

        // Gets start timestamp, casts to long, may be null
        String username = (String) request.getAttribute("username");

        // Logs HTTP method, execution time, user (or 'anon') and status code
        String logMessage = String.format(
                "Operation %s completed in %d ms - User: %s - Status: %d",
                request.getMethod(),
                duration,
                username != null ? username : "anon",
                response.getStatus());

        // Sort logs by level according to the HTTP method
        switch (request.getMethod()) {
            case "POST":
            case "PUT":
            case "DELETE":
                logger.warn(logMessage);
                break;
            default:
                logger.info(logMessage);
        }

        // Log especially sensitive operations
        if (response.getStatus() >= 400) {
            logger.error("Error in operation {}: {} - {}",
                    controller.getMethod().getName(),
                    response.getStatus(),
                    request.getRequestURI());
        }
    }
}