package com.bridgeon.app.global.web.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    // allowed origin
    private List<String> allowedOriginPatterns = List.of();

    // allowed method
    private List<String> allowedMethods = List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");

    // allowed header
    private List<String> allowedHeaders = List.of("*");

    private boolean allowCredentials = true;

    private Long maxAge = 3600L;

    private List<String> swaggerAllowedOriginPatterns = List.of();
}
