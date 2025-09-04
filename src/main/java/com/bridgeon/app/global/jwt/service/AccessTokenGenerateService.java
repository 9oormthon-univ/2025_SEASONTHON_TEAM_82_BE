package com.bridgeon.app.global.jwt.service;

import com.bridgeon.app.global.dto.auth.AuthInfo;
import com.bridgeon.app.global.jwt.properties.JwtProperties;
import com.bridgeon.app.global.jwt.usecase.TokenGenerateUseCase;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenGenerateService implements TokenGenerateUseCase {

    private final JwtProperties jwt;
    private final SecretKey secretKey;

    @Override
    public String generateToken(AuthInfo authInfo) {
        Date now = new Date();
        Long expiration = jwt.getExpirationTime().getAccessToken();
        log.info(expiration.toString());

        return Jwts.builder()
                .setSubject(authInfo.id().toString())
                .claim("provider", authInfo.provider().toString())
                .claim("role", authInfo.role().toString())
                .claim("email", authInfo.email())
                .claim("TokenType", "ACCESS")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }
}
