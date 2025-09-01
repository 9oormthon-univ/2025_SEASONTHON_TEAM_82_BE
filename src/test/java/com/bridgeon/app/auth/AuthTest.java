package com.bridgeon.app.auth;

import com.bridgeon.app.BaseTest;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.UserRepository;
import com.bridgeon.app.global.dto.auth.AuthInfo;
import com.bridgeon.app.global.enums.user.Provider;
import com.bridgeon.app.global.enums.user.Region;
import com.bridgeon.app.global.enums.user.Role;
import com.bridgeon.app.global.jwt.utils.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class AuthTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(AuthTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void 사용자_회원가입_및_토큰발급() {
        String postfix = UUID.randomUUID().toString().substring(0, 8);
        User user = new User(
                null,
                Provider.KAKAO,
                Role.USER,
                postfix + "@gmail.com",
                "브릿지ON" + postfix,
                "카카오" + postfix,
                Region.SEO,
                null,
                null,
                null
        );

        User savedUser = userRepository.save(user);
        log.info("테스트 사용자 생성: id={}, email={}", savedUser.getId(), savedUser.getEmail());

        AuthInfo authInfo = AuthInfo.from(savedUser);
        String accessToken = jwtUtils.generateAccessToken(authInfo);
        String refreshToken = jwtUtils.generateRefreshToken(authInfo);

        log.info("🔑 Access Token : {}", accessToken);
        log.info("🔑 Refresh Token : {}", refreshToken);

        Assertions.assertTrue(jwtUtils.validateToken(accessToken), "Access Token 검증 실패");
        Assertions.assertTrue(jwtUtils.validateToken(refreshToken), "Refresh Token 검증 실패");
    }

}
