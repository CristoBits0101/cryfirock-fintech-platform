package com.cryfirock.msvc.users.msvc_users.security.filter;

import com.cryfirock.msvc.users.msvc_users.security.jackson.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.cryfirock.msvc.users.msvc_users.security.config.TokenJwtConfig.*;

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

/**
 * Verifies credentials and validates requests to the servers
 * JWT filter for verifying and processing authentication tokens in requests
 * Extends BasicAuthenticationFilter for Spring Security integration
 */
public class JwtValidationFilter extends BasicAuthenticationFilter {

        /**
         * Initializes the filter with Spring's AuthenticationManager
         * 
         * @param authenticationManager Spring Security's authentication provider
         */
        public JwtValidationFilter(AuthenticationManager authenticationManager) {
                // Delegate initialization to parent
                super(authenticationManager);
        }

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain chain) throws IOException, ServletException, IOException, java.io.IOException {

                // 1. Extract Authorization header
                String header = request.getHeader(HEADER_AUTHORIZATION);

                // 2. Basic header validation
                if (header == null || !header.startsWith(PREFIX_TOKEN)) {
                        // If no token or wrong prefix, continue without authentication
                        chain.doFilter(request, response);
                        return;
                }

                // 3. Clean token (remove "Bearer " prefix)
                String token = header.replace(PREFIX_TOKEN, "");

                try {
                        // 4. JWT parsing and verification
                        Claims claims = Jwts
                                        .parser()
                                        // Secret key verification
                                        .verifyWith(SECRET_KEY)
                                        .build()
                                        .parseSignedClaims(token)
                                        .getPayload();

                        // 5. Extract username from token subject
                        String username = claims.getSubject();

                        // 6. Extract authorities from custom claim
                        Object authoritiesClaims = claims.get("authorities");

                        // 7. Convert authorities to Spring Security format
                        Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                                        new ObjectMapper()
                                                        .addMixIn(
                                                                        SimpleGrantedAuthority.class,
                                                                        SimpleGrantedAuthorityJsonCreator.class)
                                                        .readValue(authoritiesClaims
                                                                        .toString()
                                                                        .getBytes(),
                                                                        SimpleGrantedAuthority[].class));

                        // 8. Create authentication token
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        authorities);

                        // 9. Set authentication in security context
                        SecurityContextHolder
                                        .getContext()
                                        .setAuthentication(authenticationToken);

                        // 10. Continue filter chain
                        chain.doFilter(request, response);

                } catch (JwtException e) {
                        // 11. When JWT validation fails, prepare the error response
                        Map<String, String> body = new HashMap<>();
                        body.put("error", e.getMessage());
                        body.put("message", "Invalid JWT token!");

                        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType(CONTENT_TYPE);
                }
        }
}
