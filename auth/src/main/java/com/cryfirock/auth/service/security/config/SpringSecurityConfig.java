package com.cryfirock.auth.service.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cryfirock.auth.service.security.filter.JwtAuthenticationFilter;

/**
 * ============================================================================================================
 * Paso 23.1: Configuración de seguridad de Spring Security
 * ============================================================================================================
 */

// Clase de configuración para la seguridad de la aplicación
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    /**
     * ========================================================================================================
     * Paso 23.2: Atributos
     * ========================================================================================================
     */

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * ========================================================================================================
     * Paso 23.3: BEANS
     * ========================================================================================================
     */

    /**
     * Permite inyectar el gestor de autenticación
     *
     * @return instancia de AuthenticationManager
     * @throws Exception
     */
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean para el codificador de contraseñas
     *
     * @return instancia de BCryptPasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        // Usa BCrypt para encriptar y comparar contraseñas
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el acceso a rutas y establece las reglas
     *
     * @param http configuración de seguridad HTTP
     * @return reglas definidas para el filtro de seguridad
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Define el tipo de acceso a las rutas
                // Las rutas públicas no requieren autenticación
                // Las rutas privadas requieren autenticación
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/superuser")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                // Deshabilita la protección CSRF
                // No es necesaria para APIs REST sin sistema de sesión
                .csrf(csrf -> csrf.disable())
                // Configura CORS para frameworks frontend
                // Permite peticiones desde otros dominios
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // REGISTRA EL FILTRO DE AUTENTICACIÓN JWT
                // EN CADA PETICIÓN DE LOGIN AL SERVIDOR SE EJECUTARÁ ESTE FILTRO
                // LE PASAMOS EL GESTOR DE AUTENTICACIÓN A LA CLASE FILTRO
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                // Deshabilita las cookies de sesión
                // Cada petición debe ser autenticada de forma independiente
                // No se mantiene estado entre peticiones
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Construye la configuración
                // Devuelve el filtro de seguridad con las reglas definidas
                .build();
    }

    /**
     * Reglas para las solicitudes
     *
     * @return origen de configuración de CORS
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Configura el filtro de CORS
     *
     * @return bean de registro del filtro CORS
     */
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }

}
