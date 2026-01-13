package com.cryfirock.auth.interceptor;

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

import com.cryfirock.auth.security.config.TokenJwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 1. Interceptor para operaciones de usuario.
 * 2. Implementa HandlerInterceptor de Spring MVC.
 * 3. Valida tokens JWT en solicitudes a rutas protegidas.
 * 4. Registra logs de las operaciones realizadas.
 * 5. Mide el tiempo de ejecución de las solicitudes.
 */
@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {
  /**
   * 1. Logger para registrar mensajes de log.
   */
  private static final Logger logger = LoggerFactory.getLogger(UserOperationsInterceptor.class);

  /**
   * 1. Ejecuta antes del manejo de la solicitud.
   * 2. Valida el token JWT en solicitudes protegidas.
   * 3. Registra el inicio de la operación.
   * 4. Retorna false si la autenticación falla.
   * 
   * @param request  Solicitud HTTP entrante.
   * @param response Respuesta HTTP saliente.
   * @param handler  Controlador que manejará la solicitud.
   * @return true si la solicitud puede continuar, false si debe detenerse.
   * @throws Exception Si ocurre un error durante la validación.
   */
  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler) throws Exception {
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

        Claims claims = Jwts.parser()
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

  /**
   * 1. Ejecuta después del manejo de la solicitud.
   * 2. Calcula el tiempo de ejecución de la operación.
   * 3. Registra logs según el tipo de operación y estado.
   * 4. Registra errores si el estado HTTP es >= 400.
   * 
   * @param request      Solicitud HTTP entrante.
   * @param response     Respuesta HTTP saliente.
   * @param handler      Controlador que manejó la solicitud.
   * @param modelAndView Modelo y vista (puede ser null).
   * @throws Exception Si ocurre un error durante el post-procesamiento.
   */
  @Override
  public void postHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    if (!(handler instanceof HandlerMethod))
      return;

    HandlerMethod controller = (HandlerMethod) handler;

    long end = System.currentTimeMillis();
    long start = (long) request.getAttribute("start");
    long duration = end - start;

    String username = (String) request.getAttribute("username");
    String logMessage = String.format(
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
