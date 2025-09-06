package com.bridgeon.app.domain.user.dto.response;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {
}
