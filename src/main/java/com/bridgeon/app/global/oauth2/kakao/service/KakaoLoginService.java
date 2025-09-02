package com.bridgeon.app.global.oauth2.kakao.service;

import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.UserRepository;
import com.bridgeon.app.global.dto.auth.AuthInfo;
import com.bridgeon.app.global.enums.user.Provider;
import com.bridgeon.app.global.enums.user.Role;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.bridgeon.app.global.oauth2.kakao.client.KakaoOAuthClient;
import com.bridgeon.app.global.oauth2.kakao.dto.KakaoUserResponseDto;
import com.bridgeon.app.global.oauth2.kakao.usecase.KakaoLoginUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService implements KakaoLoginUseCase {

    private final KakaoOAuthClient kakaoOAuthClient;
    private final UserRepository userRepository;

    @Transactional
    public AuthInfo loginWithCode(String code) {
        String kakaoAccess = kakaoOAuthClient.exchangeCodeForAccessToken(code);

        KakaoUserResponseDto userResponse = kakaoOAuthClient.fetchUser(kakaoAccess);
        String email = userResponse.email();
        String nickname = userResponse.nickname();
        log.info("email={}, nickname={}", email, nickname);

        if (email == null || nickname == null) {
            throw new BusinessException(AuthErrorCode.PERMISSION_CONSENT);
        }

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .provider(Provider.KAKAO)
                                .role(Role.USER)
                                .email(email)
                                .name(nickname)
                                .nickname(nickname)
                                .build()
                ));

        return AuthInfo.from(user);
    }
}
