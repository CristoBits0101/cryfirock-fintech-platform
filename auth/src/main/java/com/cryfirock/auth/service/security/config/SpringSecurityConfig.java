package com.cryfirock.auth.service.security.config;

import com.cryfirock.auth.service.security.filter.JwtAutheticationFilter;

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

// Clase de configuración para la seguridad de la aplicación
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

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
                .csrf(csrf -> csrf.disable())
                // Configura CORS para frameworks frontend
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Registra el filtro de gestión de autenticación
                .addFilter(new JwtAutheticationFilter(authenticationConfiguration.getAuthenticationManager()))
                // Deshabilita las cookies de sesión
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Construye la configuración
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
