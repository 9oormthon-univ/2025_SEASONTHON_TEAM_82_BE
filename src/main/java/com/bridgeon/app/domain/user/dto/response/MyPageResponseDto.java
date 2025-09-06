package com.bridgeon.app.domain.user.dto.response;

import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.enums.user.Provider;
import com.bridgeon.app.global.enums.user.Region;
import com.bridgeon.app.global.enums.user.Role;

public record MyPageResponseDto(
    Long id,
    Provider provider,
    Role role,
    String email,
    String name,
    String nickname,
    Region region,
    InterestField interestField,
    String introduction
) {
}
