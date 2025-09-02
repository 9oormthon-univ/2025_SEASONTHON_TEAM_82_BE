package com.bridgeon.app.global.jwt.handler;

import com.bridgeon.app.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractSecurityFailureHandler {

    private final ObjectMapper objectMapper;

    protected void writeErrorResponse(
            HttpServletResponse res,
            ErrorResponse errorResponse
    ) throws IOException {
        res.setStatus(errorResponse.getHttpStatus());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(res.getWriter(), errorResponse);
    }
}
