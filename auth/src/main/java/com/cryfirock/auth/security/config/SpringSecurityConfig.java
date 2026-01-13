package com.cryfirock.auth.security.config;

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

import com.cryfirock.auth.security.filter.JwtAuthenticationFilter;
import com.cryfirock.auth.security.filter.JwtValidationFilter;
import com.cryfirock.auth.security.handler.RestAccessDeniedHandler;
import com.cryfirock.auth.security.handler.RestAuthenticationEntryPoint;

/**
 * 1. Configuración de seguridad de Spring Security.
 * 2. Habilita la seguridad a nivel de método con @EnableMethodSecurity.
 * 3. Define la cadena de filtros de seguridad.
 * 4. Configura autenticación JWT y políticas CORS.
 * 5. Establece el manejo de excepciones de seguridad.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
  /**
   * 1. Configuración de autenticación de Spring Security.
   * 2. Inyectado automáticamente por Spring.
   */
  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;

  /**
   * 1. Bean que proporciona el AuthenticationManager.
   * 2. Utilizado para autenticar usuarios en el sistema.
   * 
   * @return AuthenticationManager configurado.
   * @throws Exception Si ocurre un error al obtener el manager.
   */
  @Bean
  AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * 1. Bean que proporciona el codificador de contraseñas.
   * 2. Utiliza BCrypt para codificar contraseñas de forma segura.
   * 
   * @return PasswordEncoder con BCrypt.
   */
  @Bean
  @SuppressWarnings("unused")
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 1. Bean que configura la cadena de filtros de seguridad.
   * 2. Define reglas de autorización para endpoints.
   * 3. Configura filtros JWT de autenticación y validación.
   * 4. Establece política de sesiones sin estado (stateless).
   * 5. Configura manejo de excepciones de seguridad.
   * 
   * @param http Configurador de seguridad HTTP.
   * @return SecurityFilterChain configurada.
   * @throws Exception Si ocurre un error en la configuración.
   */
  @Bean
  @SuppressWarnings("unused")
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
    jwtAuthenticationFilter.setFilterProcessesUrl("/login");

    return http.authorizeHttpRequests(
        authz -> authz
            .requestMatchers(HttpMethod.POST, "/api/users")
            .permitAll()
            .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/{id}")
            .hasAnyRole("ADMIN", "USER")
            .requestMatchers(HttpMethod.POST, "/api/users/superuser")
            .hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/users/{id}")
            .hasAnyRole("ADMIN", "USER")
            .requestMatchers(HttpMethod.DELETE, "/api/users/{id}")
            .hasAnyRole("ADMIN", "USER")
            .anyRequest()
            .authenticated())
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .exceptionHandling(
            exception -> exception
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(new RestAccessDeniedHandler()))
        .addFilter(jwtAuthenticationFilter)
        .addFilterBefore(
            new JwtValidationFilter(authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(
            session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

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

  @Bean
  @SuppressWarnings("unused")
  FilterRegistrationBean<CorsFilter> corsFilter() {
    CorsConfigurationSource configSource = corsConfigurationSource();
    @SuppressWarnings("null")
    FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(configSource));
    corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return corsBean;
  }
}
