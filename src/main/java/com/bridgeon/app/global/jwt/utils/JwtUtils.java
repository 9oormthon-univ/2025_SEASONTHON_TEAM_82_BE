package com.bridgeon.app.global.jwt.utils;

import com.bridgeon.app.domain.user.repository.UserRepository;
import com.bridgeon.app.global.dto.auth.AuthInfo;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.bridgeon.app.global.jwt.usecase.TokenGenerateUseCase;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;

@Slf4j
@Component
public class JwtUtils {

    private final SecretKey secretKey;
    public final TokenGenerateUseCase accessTokenGenerateService;
    public final TokenGenerateUseCase refreshTokenGenerateService;
    private final UserRepository userRepository;

    public JwtUtils(
            SecretKey secretKey,
            @Qualifier("accessTokenGenerateService") TokenGenerateUseCase accessTokenGenerateService,
            @Qualifier("refreshTokenGenerateService") TokenGenerateUseCase refreshTokenGenerateService,
            UserRepository userRepository) {
        this.secretKey = secretKey;
        this.accessTokenGenerateService = accessTokenGenerateService;
        this.refreshTokenGenerateService = refreshTokenGenerateService;
        this.userRepository = userRepository;
    }


    // Access Token 생성
    public String generateAccessToken(AuthInfo authInfo) {
        return accessTokenGenerateService.generateToken(authInfo);
    }

    // Refresh Token 생성
    public String generateRefreshToken(AuthInfo authInfo) {
        return refreshTokenGenerateService.generateToken(authInfo);
    }


    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰입니다.", e);
        }
        return false;
    }


    // JWT Claims 추출
    private Claims getClaimsFromToken(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


    // JWT 인증 정보 추출
    public Authentication getAuthentication(String accessToken) {
        Claims claims = getClaimsFromToken(accessToken);
        String role = claims.get("role", String.class);

        Long userId = Long.parseLong(claims.getSubject());
        com.bridgeon.app.domain.user.entity.User entity =
                userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(AuthErrorCode.UNAUTHORIZED));

        return new UsernamePasswordAuthenticationToken(
                entity, // 이제 엔티티가 principal
                accessToken,
                Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }
}
