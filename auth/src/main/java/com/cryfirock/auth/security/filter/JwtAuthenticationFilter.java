package com.cryfirock.auth.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cryfirock.auth.dto.UserLoginDto;
import static com.cryfirock.auth.security.config.TokenJwtConfig.CONTENT_TYPE;
import static com.cryfirock.auth.security.config.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.cryfirock.auth.security.config.TokenJwtConfig.PREFIX_TOKEN;
import static com.cryfirock.auth.security.config.TokenJwtConfig.SECRET_KEY;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 1. Filtro de autenticación JWT.
 * 2. Extiende UsernamePasswordAuthenticationFilter de Spring Security.
 * 3. Procesa solicitudes de login y genera tokens JWT.
 * 4. Maneja autenticación exitosa y fallida.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // 1. Gestor de autenticación de Spring Security.
    private final AuthenticationManager authenticationManager;

    /**
     * 1. Constructor que inyecta el gestor de autenticación.
     *
     * @param authenticationManager Gestor de autenticación.
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 1. Intenta autenticar al usuario con las credenciales proporcionadas.
     * 2. Lee el cuerpo de la solicitud como UserLoginDto.
     * 3. Crea un token de autenticación con usuario y contraseña.
     * 4. Lanza AuthenticationServiceException si hay errores de lectura.
     *
     * @param request Solicitud HTTP con las credenciales.
     * @param response Respuesta HTTP.
     * @return Authentication con el resultado de la autenticación.
     * @throws AuthenticationException Si la autenticación falla.
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;

        try {
            UserLoginDto credentials = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginDto.class);

            username = credentials.username();
            password = credentials.password();
        } catch (StreamReadException e) {
            logger.warn("Error leyendo el flujo de entrada del login ", e);
            throw new AuthenticationServiceException("Invalid login payload ", e);
        } catch (DatabindException e) {
            logger.warn("Error mapeando JSON de login al DTO", e);
            throw new AuthenticationServiceException("Invalid login JSON ", e);
        } catch (IOException e) {
            logger.error("Error de E/S leyendo el cuerpo de la petición de login", e);
            throw new AuthenticationServiceException("I/O error", e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 1. Maneja la autenticación exitosa.
     * 2. Genera un token JWT con las autoridades del usuario.
     * 3. Añade el token al encabezado Authorization de la respuesta.
     * 4. Escribe un cuerpo JSON con el token y mensaje de bienvenida.
     *
     * @param request Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @param chain Cadena de filtros.
     * @param authResult Resultado de la autenticación exitosa.
     * @throws IOException Si ocurre un error de E/S.
     * @throws ServletException Si ocurre un error del servlet.
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
                .getPrincipal();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        List<Map<String, String>> authorities = roles.stream()
                .map(role -> Map.of("authority", role.getAuthority()))
                .collect(Collectors.toList());

        Claims claims = Jwts.claims().add("authorities", authorities).add("username", username)
                .build();

        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + " " + token);

        Map<String, String> body = new HashMap<>();

        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Welcome %s! ", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * 1. Maneja la autenticación fallida.
     * 2. Construye un cuerpo JSON con el mensaje de error.
     * 3. Responde con código de estado 401 Unauthorized.
     *
     * @param request Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @param failed Excepción de autenticación fallida.
     * @throws IOException Si ocurre un error de E/S.
     * @throws ServletException Si ocurre un error del servlet.
     */
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();

        body.put("message", "Authentication failed, credentials are not correct.");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }
}
