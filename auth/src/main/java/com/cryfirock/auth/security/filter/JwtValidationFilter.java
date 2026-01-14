package com.cryfirock.auth.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.cryfirock.auth.security.config.TokenJwtConfig.CONTENT_TYPE;
import static com.cryfirock.auth.security.config.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.cryfirock.auth.security.config.TokenJwtConfig.PREFIX_TOKEN;
import static com.cryfirock.auth.security.config.TokenJwtConfig.SECRET_KEY;
import com.cryfirock.auth.security.jackson.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 1. Filtro de validación de tokens JWT.
 * 2. Extiende BasicAuthenticationFilter de Spring Security.
 * 3. Verifica y procesa tokens JWT en solicitudes entrantes.
 * 4. Establece el contexto de seguridad si el token es válido.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public class JwtValidationFilter extends BasicAuthenticationFilter {
    /**
     * 1. ObjectMapper configurado para deserializar SimpleGrantedAuthority.
     * 2. Utiliza un mixin para la creación de autoridades desde JSON.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class);

    /**
     * 1. Constructor que inyecta el gestor de autenticación.
     *
     * @param authenticationManager Gestor de autenticación.
     */
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 1. Filtra las solicitudes entrantes para validar el token JWT.
     * 2. Si no hay token, continúa con la cadena de filtros.
     * 3. Verifica el token y extrae las autoridades del usuario.
     * 4. Establece el contexto de seguridad con la autenticación.
     * 5. Responde con error 401 si el token es inválido.
     *
     * @param request  Solicitud HTTP entrante.
     * @param response Respuesta HTTP saliente.
     * @param chain    Cadena de filtros.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(PREFIX_TOKEN.length()).trim();

        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();

            String username = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities");
            Collection<? extends GrantedAuthority> authorities = extractAuthorities(authoritiesClaims);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            chain.doFilter(request, response);
        } catch (JwtException | JsonProcessingException e) {
            Map<String, String> body = new HashMap<>();

            body.put("error", e.getMessage());
            body.put("message", "Invalid JWT token!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
    }

    /**
     * 1. Extrae las autoridades del claim de autoridades del token.
     * 2. Soporta colecciones y arrays de autoridades.
     * 3. Convierte cada autoridad a GrantedAuthority.
     *
     * @param authoritiesClaims Claim con las autoridades del token.
     * @return Colección de GrantedAuthority.
     * @throws IOException Si ocurre un error al deserializar.
     */
    private Collection<? extends GrantedAuthority> extractAuthorities(Object authoritiesClaims) throws IOException {
        if (authoritiesClaims instanceof Collection<?> collection) {
            return collection.stream().map(this::toGrantedAuthority).collect(Collectors.toList());
        }

        SimpleGrantedAuthority[] authorities = OBJECT_MAPPER.readValue(
                OBJECT_MAPPER.writeValueAsBytes(authoritiesClaims), SimpleGrantedAuthority[].class);

        return Arrays.asList(authorities);
    }

    /**
     * 1. Convierte un objeto de autoridad a GrantedAuthority.
     * 2. Soporta mapas con clave "authority" y valores directos.
     *
     * @param authorityClaim Objeto con la información de la autoridad.
     * @return GrantedAuthority con la autoridad extraída.
     */
    private GrantedAuthority toGrantedAuthority(Object authorityClaim) {
        boolean isMap = authorityClaim instanceof Map<?, ?> map && map.containsKey("authority");

        if (isMap) {
            Map<?, ?> map = (Map<?, ?>) authorityClaim;
            return new SimpleGrantedAuthority(String.valueOf(map.get("authority")));
        }

        return new SimpleGrantedAuthority(String.valueOf(authorityClaim));
    }
}
