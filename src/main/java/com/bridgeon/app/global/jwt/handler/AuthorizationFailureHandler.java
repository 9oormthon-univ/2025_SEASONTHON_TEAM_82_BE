package com.bridgeon.app.global.jwt.handler;

import com.bridgeon.app.global.exception.ErrorResponse;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthorizationFailureHandler extends AbstractSecurityFailureHandler implements AccessDeniedHandler {

    public AuthorizationFailureHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void handle(
            HttpServletRequest req,
            HttpServletResponse res,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        log.error("No Authorities", accessDeniedException);
        writeErrorResponse(res, ErrorResponse.of(AuthErrorCode.ACCESS_DENIED));
    }
}
