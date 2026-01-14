package com.cryfirock.auth.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 1. Interceptor para operaciones de usuario.
 * 2. Implementa HandlerInterceptor de Spring MVC.
 * 3. Registra logs de las operaciones realizadas.
 * 4. Mide el tiempo de ejecución de las solicitudes.
 * 5. La seguridad es manejada por Spring Security.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {
    /**
     * 1. Logger para registrar mensajes de log.
     */
      private static final Logger logger = LoggerFactory.getLogger(UserOperationsInterceptor.class);

    /**
     * 1. Ejecuta antes del manejo de la solicitud.
     * 2. Registra el inicio de la operación y el tiempo.
     * 3. Obtiene el usuario del contexto de Spring Security.
     * 4. La validación de seguridad es manejada por Spring Security.
     *
     * @param request  Solicitud HTTP entrante.
     * @param response Respuesta HTTP saliente.
     * @param handler  Controlador que manejará la solicitud.
     * @return true para continuar con la cadena de interceptores.
     */
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        // Obtener usuario del contexto de Spring Security.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated()) ? auth.getName() : "anonymous";
        request.setAttribute("username", username);

        logger.info(
                "Starting operation {} on {} - Controller: {} - User: {}",
                method,
                endpoint,
                handlerMethod.getMethod().getName(),
                username);

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
     */
    @Override
    public void postHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable ModelAndView modelAndView) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return;
        }

        Long start = (Long) request.getAttribute("start");
        if (start == null) {
            return;
        }

        long duration = System.currentTimeMillis() - start;
        String username = (String) request.getAttribute("username");
        int status = response.getStatus();

        String logMessage = String.format(
                "Operation %s on %s completed in %d ms - User: %s - Status: %d",
                request.getMethod(),
                request.getRequestURI(),
                duration,
                username != null ? username : "anonymous",
                status);

        // Operaciones de escritura se registran como WARN para auditoría.
        if (isWriteOperation(request.getMethod())) {
            logger.warn(logMessage);
        } else {
            logger.info(logMessage);
        }

        // Errores se registran adicionalmente.
        if (status >= 400) {
            logger.error(
                    "Error in operation {}: {} - {}",
                    handlerMethod.getMethod().getName(),
                    status,
                    request.getRequestURI());
        }
    }

    /**
     * 1. Verifica si el método HTTP es una operación de escritura.
     * 2. POST, PUT, PATCH y DELETE son operaciones de escritura.
     *
     * @param method Método HTTP.
     * @return true si es operación de escritura.
     */
    private boolean isWriteOperation(String method) {
        return "POST".equals(method) || "PUT".equals(method)
                || "PATCH".equals(method) || "DELETE".equals(method);
    }
}
