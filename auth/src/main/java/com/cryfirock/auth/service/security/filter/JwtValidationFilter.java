package com.cryfirock.auth.service.security.filter;

import com.cryfirock.auth.service.security.jackson.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.cryfirock.auth.service.security.config.TokenJwtConfig.*;

/**
 * Verifica credenciales y valida solicitudes al servidor
 * Filtro JWT para verificar y procesar tokens de autenticación en solicitudes
 * Extiende BasicAuthenticationFilter para integrarse con Spring Security
 */
public class JwtValidationFilter extends BasicAuthenticationFilter {

    /**
     * Inicializa el filtro con el AuthenticationManager de Spring
     *
     * @param authenticationManager proveedor de autenticación de Spring Security
     */
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        // Delegar la inicialización a la clase padre
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        // 1. Extraer el encabezado Authorization
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // 2. Validación básica del encabezado
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            // Si no hay token o el prefijo es incorrecto, continúa sin autenticación
            chain.doFilter(request, response);
            return;
        }

        // 3. Limpia el token (elimina el prefijo "Bearer ")
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            // 4. Análisis y verificación del JWT
            Claims claims = Jwts
                    .parser()
                    // Verificación de la clave secreta
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 5. Extrae el usuario del sujeto del token
            String username = claims.getSubject();

            // 6. Extrae las autoridades del claim personalizado
            Object authoritiesClaims = claims.get("authorities");

            // 7. Convierte las autoridades al formato de Spring Security
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper()
                            .addMixIn(
                                    SimpleGrantedAuthority.class,
                                    SimpleGrantedAuthorityJsonCreator.class)
                            .readValue(authoritiesClaims
                                    .toString()
                                    .getBytes(),
                                    SimpleGrantedAuthority[].class));

            // 8. Crea el token de autenticación
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities);

            // 9. Establece la autenticación en el contexto de seguridad
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationToken);

            // 10. Continúa con la cadena de filtros
            chain.doFilter(request, response);

        } catch (JwtException e) {
            // 11. Cuando la validación del JWT falla, prepara la respuesta de error
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "Invalid JWT token!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
    }
}
