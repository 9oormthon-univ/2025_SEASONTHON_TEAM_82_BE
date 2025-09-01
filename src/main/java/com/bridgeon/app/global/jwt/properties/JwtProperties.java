package com.bridgeon.app.global.jwt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private ExpirationTime expirationTime;

    @Getter
    @Setter
    public static class ExpirationTime {
        private Long accessToken;
        private Long refreshToken;
    }
}
