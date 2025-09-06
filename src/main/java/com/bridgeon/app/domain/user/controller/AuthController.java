package com.bridgeon.app.domain.user.controller;

import com.bridgeon.app.domain.user.dto.response.LoginResponseDto;
import com.bridgeon.app.global.dto.auth.AuthInfo;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.jwt.properties.JwtProperties;
import com.bridgeon.app.global.jwt.utils.JwtUtils;
import com.bridgeon.app.global.oauth2.kakao.usecase.KakaoLoginUseCase;
import com.bridgeon.app.global.web.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KakaoLoginUseCase kakaoLoginUseCase;
    private final JwtUtils jwtUtils;
    private final JwtProperties jwtProperties;

    @GetMapping("/kakao/login")
    public ResponseEntity<Void> kakaoSignIn(
            @RequestParam String code,
            @RequestParam(required = false) String state,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        AuthInfo auth = kakaoLoginUseCase.loginWithCode(code);

        String accessToken = jwtUtils.generateAccessToken(auth);
        String refreshToken = jwtUtils.generateRefreshToken(auth);

        long accessTokenMaxAgeSec = jwtProperties.getExpirationTime().getAccessToken() / 1000;
        long refreshTokenMaxAgeSec = jwtProperties.getExpirationTime().getRefreshToken() / 1000;

        boolean isHttps = request.isSecure() || "https".equalsIgnoreCase(request.getHeader("X-Forwarded-Proto"));

        String accessName  = isHttps ? "__Host-access_token"  : "access_token";
        String refreshName = isHttps ? "__Host-refresh_token" : "refresh_token";
        boolean secure = isHttps;
        String sameSite = isHttps ? "None" : "Lax";

        String c1 = CookieUtils.authCookie(accessName, accessToken,  accessTokenMaxAgeSec, true, secure, sameSite);
        String c2 = CookieUtils.authCookie(refreshName, refreshToken, refreshTokenMaxAgeSec, true, secure, sameSite);

        response.addHeader("Set-Cookie", c1);
        response.addHeader("Set-Cookie", c2);

        String next = "/onboarding";
        if (state != null && state.startsWith("next=")) {
            String cand = state.substring("next=".length());
            if (cand.startsWith("/")) next = cand;
        }

        // 4) 프론트(로컬)로 리다이렉트하면서 토큰을 쿼리로 넘김 (개발용 간단 방식)
        String target = "http://localhost:5173" + next
                + "?at=" + URLEncoder.encode(accessToken, StandardCharsets.UTF_8)
                + "&rt=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, target)
                .build();
    }

    @GetMapping("/token")
    public ResponseDto<LoginResponseDto> readTokenFromCookie(
            @CookieValue(name="__Host-access_token",  required = false) String hostAccess,
            @CookieValue(name="access_token",        required = false) String access,
            @CookieValue(name="__Host-refresh_token",required = false) String hostRefresh,
            @CookieValue(name="refresh_token",       required = false) String refresh
    ) {
        String at = StringUtils.hasText(hostAccess)  ? hostAccess  : access;
        String rt = StringUtils.hasText(hostRefresh) ? hostRefresh : refresh;
        return ResponseDto.success(
                HttpStatus.OK,
                "토큰 조회",
                new LoginResponseDto(at, rt)
        );
    }


    @PostMapping("/logout")
    public ResponseDto<?> kakaoLogout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        boolean isHttps = request.isSecure() || "https".equalsIgnoreCase(request.getHeader("X-Forwarded-Proto"));

        String accessName = isHttps ? "__Host-access_token" : "access_token";
        String refreshName = isHttps ? "__Host-refresh_token" : "refresh_token";
        String sameSite = isHttps ? "None" : "Lax";
        boolean secure = isHttps;

        response.addHeader("Set-Cookie", CookieUtils.deleteCookie(accessName, secure, sameSite));
        response.addHeader("Set-Cookie", CookieUtils.deleteCookie(refreshName, secure, sameSite));

        return ResponseDto.success(org.springframework.http.HttpStatus.OK, "로그아웃 완료");
    }
}
