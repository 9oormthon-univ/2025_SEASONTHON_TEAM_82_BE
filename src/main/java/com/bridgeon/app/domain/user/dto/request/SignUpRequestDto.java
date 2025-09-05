package com.bridgeon.app.domain.user.dto.request;

import com.bridgeon.app.global.enums.user.Provider;
import com.bridgeon.app.global.enums.user.Role;

public record SignUpRequestDto(
        Provider provider,
        Role role,
        String email,
        String name,
        String nickname
) {
}
