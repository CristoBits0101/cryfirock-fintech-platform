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

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;

        try {
            UserLoginDto credentials = new ObjectMapper()
                    .readValue(
                            request.getInputStream(),
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
                username,
                password);

        return authenticationManager.authenticate(authenticationToken);
    }

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

        List<Map<String, String>> authorities = roles
                .stream()
                .map(role -> Map.of("authority", role.getAuthority()))
                .collect(Collectors.toList());

        Claims claims = Jwts
                .claims()
                .add("authorities", authorities)
                .add("username", username)
                .build();

        String token = Jwts
                .builder()
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
