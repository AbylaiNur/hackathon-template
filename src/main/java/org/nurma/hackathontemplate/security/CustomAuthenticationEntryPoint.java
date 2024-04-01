package org.nurma.hackathontemplate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.nurma.hackathontemplate.dto.response.CustomErrorResponse;
import org.nurma.hackathontemplate.util.ExceptionTitle;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTitle(ExceptionTitle.AUTHENTICATION);

        if (authException instanceof InsufficientAuthenticationException) {
            customErrorResponse.setDetail(ExceptionTitle.JWT_TOKEN_INVALID);
        } else {
            customErrorResponse.setDetail(authException.getMessage());
            log.warn("Unexpected authentication exception", authException);
        }

        response.getWriter().write(objectMapper.writeValueAsString(customErrorResponse));
    }
}
