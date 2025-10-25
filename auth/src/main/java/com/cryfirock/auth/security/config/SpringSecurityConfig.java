package com.cryfirock.auth.security.config;

import com.cryfirock.auth.security.filter.JwtAuthenticationFilter;
import com.cryfirock.auth.security.filter.JwtValidationFilter;
import com.cryfirock.auth.security.handler.RestAccessDeniedHandler;
import com.cryfirock.auth.security.handler.RestAuthenticationEntryPoint;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * =====================================================================================================
 * Paso 18.1: Configuración principal de seguridad con JWT y CORS
 * =====================================================================================================
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    /**
     * =================================================================================================
     * Paso 18.2: Atributos
     * =================================================================================================
     */
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * =================================================================================================
     * Paso 18.3: Beans principales de autenticación
     * =================================================================================================
     */
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * =================================================================================================
     * Paso 18.4: Cadena de filtros de Spring Security
     * =================================================================================================
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return http
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
                        .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .anyRequest()
                        .authenticated())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                        .accessDeniedHandler(new RestAccessDeniedHandler()))
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(new JwtValidationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /**
     * =================================================================================================
     * Paso 18.5: Configuración CORS
     * =================================================================================================
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * =================================================================================================
     * Paso 18.6: Registro del filtro CORS con máxima prioridad
     * =================================================================================================
     */
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }

}
