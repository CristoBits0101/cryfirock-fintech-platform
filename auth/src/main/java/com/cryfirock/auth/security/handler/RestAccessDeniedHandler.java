package com.cryfirock.auth.security.handler;
import static com.cryfirock.auth.security.config.TokenJwtConfig.CONTENT_TYPE;
import com.cryfirock.auth.model.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Forbidden");
        error.setMessage(accessDeniedException.getMessage());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(CONTENT_TYPE);
        objectMapper.writeValue(response.getWriter(), error);
    }
}
