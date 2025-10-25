package com.cryfirock.auth.interceptor;

import com.cryfirock.auth.security.config.TokenJwtConfig;
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

@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(UserOperationsInterceptor.class);

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) return true;
    HandlerMethod controller = (HandlerMethod) handler;
    String endpoint = request.getRequestURI();
    String method = request.getMethod();
    long start = System.currentTimeMillis();
    request.setAttribute("start", start);

    if (!endpoint.equals("/api/users") || !method.equals("POST")) {
      String authHeader = request.getHeader("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        logger.warn("Unauthorized access attempt to {}", endpoint);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT requerido");
        return false;
      }

      try {
        String token = authHeader.substring(7);

        Claims claims =
            Jwts.parser()
                .verifyWith(TokenJwtConfig.SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (claims.getExpiration().before(new Date())) {
          logger.warn("Token expired for user {}", claims.getSubject());
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
          return false;
        }

        request.setAttribute("username", claims.getSubject());
      } catch (JwtException | IOException | IllegalArgumentException e) {
        logger.error("Error validating JWT token: {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        return false;
      }
    }

    logger.info(
        "Starting operation {} on {} - Controller: {}",
        method,
        endpoint,
        controller.getMethod().getName());

    return true;
  }

  @Override
  public void postHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler,
      @Nullable ModelAndView modelAndView)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) return;

    HandlerMethod controller = (HandlerMethod) handler;

    long end = System.currentTimeMillis();
    long start = (long) request.getAttribute("start");
    long duration = end - start;

    String username = (String) request.getAttribute("username");
    String logMessage =
        String.format(
            "Operation %s completed in %d ms - User: %s - Status: %d",
            request.getMethod(),
            duration,
            username != null ? username : "anon",
            response.getStatus());

    switch (request.getMethod()) {
      case "POST", "PUT", "DELETE" -> logger.warn(logMessage);
      default -> logger.info(logMessage);
    }

    if (response.getStatus() >= 400) {
      logger.error(
          "Error in operation {}: {} - {}",
          controller.getMethod().getName(),
          response.getStatus(),
          request.getRequestURI());
    }
  }
}
