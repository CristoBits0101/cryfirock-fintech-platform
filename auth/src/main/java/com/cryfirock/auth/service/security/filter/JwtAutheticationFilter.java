package com.cryfirock.auth.service.security.filter;

import com.cryfirock.auth.service.entities.User;
import com.cryfirock.auth.service.service.JpaUserDetailsService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import static com.cryfirock.auth.service.security.config.TokenJwtConfig.*;

// Este filtro autentica usuarios y genera tokens durante el proceso de inicio de sesión
public class JwtAutheticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Atributos
     */
    private AuthenticationManager authenticationManager;

    /**
     * Constructores
     *
     * @param authenticationManager
     */
    public JwtAutheticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Primera función que se ejecuta en el proceso de inicio de sesión
     * Recibe usuario y contraseña
     * {@link JwtAutheticationFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)}
     *
     * Segunda función que se ejecuta en el proceso de inicio de sesión
     * Busca el usuario en la base de datos
     * {@link JpaUserDetailsService#loadUserByUsername(String)}
     *
     * Tercera función que se ejecuta en el proceso de inicio de sesión
     * Valida la contraseña y roles del usuario
     * {@link AuthenticationManager#authenticate(Authentication)}
     *
     * Cuarta función que se ejecuta en el proceso de inicio de sesión
     * Genera el token JWT tras la autenticación exitosa
     * {@link JwtAutheticationFilter#successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
     *
     * Intenta autenticar a un usuario
     * - Analiza el cuerpo de la solicitud para obtener las credenciales
     * - Delega la autenticación al AuthenticationManager proporcionado
     *
     * @param request  solicitud HTTP que contiene las credenciales
     * @param response respuesta HTTP para enviar el resultado de la autenticación
     * @return objeto con los datos del usuario e información de roles
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        // Declaración de un usuario nulo que no apunta a ningún objeto
        User user = null;

        // Crea dos variables para las credenciales del usuario
        String username = null;
        String password = null;

        try {
            // Intenta convertir el cuerpo de la solicitud entrante en un objeto User
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            // Extrae el usuario y la contraseña del objeto User
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            // Maneja la excepción si no se puede leer el flujo de entrada
            e.printStackTrace();
        } catch (DatabindException e) {
            // Maneja la excepción si no se puede mapear la entrada a la clase User
            e.printStackTrace();
        } catch (IOException e) {
            // Maneja otras excepciones de entrada/salida
            e.printStackTrace();
        }

        // Crea un token de autenticación con el usuario y la contraseña
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password);

        // Autentica al usuario usando el AuthenticationManager
        // Verifica que las credenciales sean válidas y coincidan con las almacenadas
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * Se ejecuta automáticamente después de autenticar y no necesita ser invocada
     * Genera un token JWT tras la autenticación exitosa del usuario
     * Lo añade al encabezado de autorización de la respuesta
     * También prepara y envía una respuesta
     * Respuesta que contiene el token, el usuario y un mensaje de bienvenida
     *
     * @param request  solicitud HTTP
     * @param response respuesta HTTP
     * @param chain    cadena de filtros
     * @param authResult resultado de la autenticación
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // Obtiene el usuario autenticado
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
                .getPrincipal();

        // Obtiene el nombre de usuario del usuario autenticado
        String username = user.getUsername();

        // Obtiene los roles (autoridades) del usuario autenticado
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        // Crea un objeto claims de JWT para almacenar información adicional
        Claims claims = Jwts
                .claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();

        /**
         * Genera un token JWT basado en los datos
         *
         * 1. Empieza a construir el token
         * 2. Define el sujeto
         * 3. Agrega claims, información adicional
         * 4. Establece la fecha de expiración
         * 5. Establece la fecha de emisión
         * 6. Firma el token
         * 7. Genera el token compacto
         */
        String token = Jwts
                .builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        // Coloca el token en el encabezado Authorization de la respuesta
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + " " + token);

        // Prepara un cuerpo de respuesta con el token, el usuario y un mensaje
        Map<String, String> body = new HashMap<>();

        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Welcome %s! ", username));

        // Escribe el cuerpo de la respuesta como JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

        // Configura el tipo y estado de la respuesta
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Esta función se ejecuta cuando falla la autenticación.
     * Prepara y envía una respuesta de error al cliente.
     *
     * - Establece el código de estado HTTP en 401 (No autorizado).
     * - Escribe el cuerpo de la respuesta como JSON.
     *
     * @param request  la solicitud HTTP que provocó el fallo de autenticación
     * @param response la respuesta HTTP que se devolverá al cliente
     * @param failed   la excepción con detalles del fallo de autenticación
     */
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // Construye un cuerpo de respuesta que contiene un mensaje de error
        Map<String, String> body = new HashMap<>();

        body.put("message", "Authentication failed, credentials are not correct.");
        body.put("error", failed.getMessage());

        // Incluye el motivo específico del fallo de autenticación
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }

}
