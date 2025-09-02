package com.bridgeon.app.global.oauth2.kakao.usecase;

import com.bridgeon.app.global.dto.auth.AuthInfo;

public interface KakaoLoginUseCase {
    AuthInfo loginWithCode(String code);
}
