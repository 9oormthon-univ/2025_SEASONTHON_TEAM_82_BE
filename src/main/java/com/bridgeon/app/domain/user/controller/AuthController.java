package com.bridgeon.app.domain.user.controller;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KakaoLoginUseCase kakaoLoginUseCase;
    private final JwtUtils jwtUtils;
    private final JwtProperties jwtProperties;

    @GetMapping("/login")
    public ResponseEntity<Void> kakaoSignIn(
            @RequestParam String code,
            @RequestParam(required = false) String next,
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

        String frontendBaseUrl = "http://localhost:5173";
        String target = frontendBaseUrl + (next != null && next.startsWith("/") ? next : "/onboarding");

        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, target)
                .build();
//        return ResponseDto.success(
//                HttpStatus.OK,
//                "로그인 성공"
//        );
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
