package com.cryfirock.msvc.users.msvc_users.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
/**
 * Dependencies
 */
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

import com.cryfirock.msvc.users.msvc_users.security.filter.JwtAutheticationFilter;

// Configuration class for application security
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

        @Autowired
        private AuthenticationConfiguration authenticationConfiguration;

        /**
         * Enables injection of the authentication management filter
         * 
         * @return authenticationConfiguration
         * @throws Exception
         */
        @Bean
        AuthenticationManager authenticationManager() throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        /**
         * Beans
         * 
         * @return BCryptPasswordEncoder
         */
        @Bean
        PasswordEncoder passwordEncoder() {
                // Use BCrypt to encrypt and compare passwords
                return new BCryptPasswordEncoder();
        }

        /**
         * Configure route access and set rules
         * 
         * @param http
         * @return http rules
         * @throws Exception
         */
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                // Sets the type of access to the routes
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
                                // Disable CSRF protection
                                .csrf(csrf -> csrf
                                                .disable())
                                // Config cors for frontend frameworks
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                // Registering the authentication management filter
                                .addFilter(new JwtAutheticationFilter(
                                                authenticationConfiguration.getAuthenticationManager()))
                                // Disable session cookies
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                // Build the configuration
                                .build();
        }

        /**
         * Request rules
         * 
         * @return source
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
         * Config cors filter
         * 
         * @return corsBean
         */
        @Bean
        FilterRegistrationBean<CorsFilter> corsFilter() {
                FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                                new CorsFilter(corsConfigurationSource()));
                corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
                return corsBean;
        }

}
