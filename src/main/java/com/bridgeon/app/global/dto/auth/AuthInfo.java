package com.bridgeon.app.global.dto.auth;

import com.bridgeon.app.global.enums.user.Provider;
import com.bridgeon.app.global.enums.user.Role;

public record AuthInfo(
        Long id,
        Provider provider,
        Role role,
        String email
) {}