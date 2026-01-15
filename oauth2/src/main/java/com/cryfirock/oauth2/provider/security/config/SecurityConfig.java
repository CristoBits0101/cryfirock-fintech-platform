package com.cryfirock.oauth2.provider.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 1. Configuración de seguridad para el microservicio OAuth2.
 * 2. Define las reglas de acceso a los endpoints.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-14
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @param http Configuración de seguridad HTTP.
     * @return SecurityFilterChain configurado.
     * @throws Exception Si ocurre un error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso público a los endpoints de validación.
                .requestMatchers("/api/oauth2/validate/**").permitAll()
                // Permitir actuator health.
                .requestMatchers("/actuator/health").permitAll()
                // Cualquier otra solicitud requiere autenticación.
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
