package com.bridgeon.app.global.jwt.handler;

import com.bridgeon.app.global.exception.ErrorResponse;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFailureHandler extends AbstractSecurityFailureHandler implements AuthenticationEntryPoint {

    public AuthenticationFailureHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void commence(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException authException
    ) throws IOException {
        writeErrorResponse(res, ErrorResponse.of(AuthErrorCode.UNAUTHORIZED));
    }
}
