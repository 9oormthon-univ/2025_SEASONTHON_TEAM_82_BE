package com.bridgeon.app.global.jwt.usecase;

import com.bridgeon.app.global.dto.auth.AuthInfo;

public interface TokenGenerateUseCase {
    String generateToken(AuthInfo authInfo);
}