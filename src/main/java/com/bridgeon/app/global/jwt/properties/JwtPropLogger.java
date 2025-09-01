package com.bridgeon.app.global.jwt.properties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtPropLogger implements CommandLineRunner {

    private final JwtProperties jwt;

    @Override public void run(String... args) {
        log.info("jwt.secret.len={}", jwt.getSecret() == null ? null : jwt.getSecret().length());
        log.info("accessToken={}", jwt.getExpirationTime() == null ? null : jwt.getExpirationTime().getAccessToken());
        log.info("refreshToken={}", jwt.getExpirationTime() == null ? null : jwt.getExpirationTime().getRefreshToken());
    }
}
