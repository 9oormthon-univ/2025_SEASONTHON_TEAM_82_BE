package com.bridgeon.app.global.dto.auth;

import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.enums.user.Provider;
import com.bridgeon.app.global.enums.user.Role;
import lombok.Builder;

@Builder
public record AuthInfo(
        Long id,
        Provider provider,
        Role role,
        String email
) {
    public static AuthInfo from(User user) {
        return AuthInfo.builder()
                .id(user.getId())
                .provider(user.getProvider())
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
}