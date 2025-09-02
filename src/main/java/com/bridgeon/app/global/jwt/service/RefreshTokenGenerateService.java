package com.bridgeon.app.global.jwt.service;

import com.bridgeon.app.global.dto.auth.AuthInfo;
import com.bridgeon.app.global.jwt.properties.JwtProperties;
import com.bridgeon.app.global.jwt.usecase.TokenGenerateUseCase;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenGenerateService implements TokenGenerateUseCase {

    private final JwtProperties jwt;
    private final SecretKey secretKey;

    @Override
    public String generateToken(AuthInfo authInfo) {
        Date now = new Date();
        Long expirationn = jwt.getExpirationTime().getRefreshToken();

        return Jwts.builder()
                .setSubject(authInfo.id().toString())
                .claim("role", authInfo.role().toString())
                .claim("TokenType", "REFRESH")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationn))
                .signWith(secretKey)
                .compact();
    }
}
