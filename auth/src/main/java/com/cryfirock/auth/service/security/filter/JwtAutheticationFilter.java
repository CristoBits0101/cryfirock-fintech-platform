package com.cryfirock.msvc.users.msvc_users.security.filter;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.User;
import com.cryfirock.msvc.users.msvc_users.services.JpaUserDetailsService;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.cryfirock.msvc.users.msvc_users.security.config.TokenJwtConfig.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// This filter authenticates users and generates tokens during the login process
public class JwtAutheticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Attributes
     */
    private AuthenticationManager authenticationManager;

    /**
     * Constructors
     * 
     * @param authenticationManager
     */
    public JwtAutheticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * First function to run in the login process
     * Receive username and password
     * {@link JwtAutheticationFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)}
     * 
     * Second function to be executed in the login process
     * Search for the user in the database
     * {@link JpaUserDetailsService#loadUserByUsername(String)}
     * 
     * Third function to be executed in the login process
     * Valida la contraseña y roles del usuario
     * {@link AuthenticationManager#authenticate(Authentication)}
     * 
     * Fourth function to be executed in the login process
     * Generates the JWT token after successful authentication
     * {@link JwtAutheticationFilter#successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
     * 
     * Attempts to authenticate a user
     * - Parses the request body to obtain the username and password credentials
     * - Delegates authentication to the provided AuthenticationManager
     * 
     * @param request  HTTP request containing login credentials
     * @param response HTTP response to send authentication result
     * @return Object with user data and role information
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        // Declaration of a null user that does not point to any object
        User user = null;

        // Create two variables for the user credentials
        String username = null;
        String password = null;

        try {
            // Attempt to parse the incoming request's body into a User object
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            // Extract the username and password from the user object
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            // Handle exception if the input stream cannot be read
            e.printStackTrace();
        } catch (DatabindException e) {
            // Handle exception if the input cannot be mapped to the User class
            e.printStackTrace();
        } catch (IOException e) {
            // Handle other I/O exceptions
            e.printStackTrace();
        }

        // Create an authentication token with the username and password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password);

        // Authenticate the user using the provided AuthenticationManager
        // Verify that the credentials are valid and match those stored in the database
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * It runs automatically after authentication and doesn't need to be called
     * Generates a JWT token upon successful authentication of the user
     * Adds it to the authorization header of the response
     * It also prepares and sends a response
     * Response containing the token, username and a welcome message
     * 
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // Retrieve the authenticated user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
                .getPrincipal();

        // Get the username from the authenticated user
        String username = user.getUsername();

        // Retrieve the roles (authorities) of the authenticated user
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        // Create a JWT claims object to store additional information
        Claims claims = Jwts
                .claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();

        /**
         * Generate a JWT token based on the data
         * 
         * 1. Start building the token
         * 2. Define the subject
         * 3. Add claims, additional information
         * 4. Set the expiration date
         * 5. Set the issue date
         * 6. Sign the token
         * 7. Generates the compact token
         */
        String token = Jwts
                .builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        // Set the token in the Authorization header of the response
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + " " + token);

        // Prepare a response body with the token, username, and a message
        Map<String, String> body = new HashMap<>();

        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Welcome %s! ", username));

        // Write the response body as JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

        // Set the response type and status
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * This function is executed when authentication fails.
     * It prepares and sends an error response to the client.
     * 
     * - Sets the HTTP status code to 401 (Unauthorized).
     * - Writes the response body as JSON.
     * 
     * @param request  The HTTP request that led to the authentication failure
     * @param response The HTTP response to send back to the client
     * @param failed   The exception containing details about the auth failure
     */
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // Constructs a response body containing an error message
        Map<String, String> body = new HashMap<>();

        body.put("message", "Authentication failed, credentials are not correct.");
        body.put("error", failed.getMessage());

        // Includes the specific authentication failure reason
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }

}
