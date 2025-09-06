package com.bridgeon.app.domain.user.dto.response;

import com.bridgeon.app.domain.user.entity.User;

import java.time.LocalDateTime;

public record ApplicantProfileResponseDto(
        Long userId,
        String name,
        String nickname,
        String email,
        String region,
        String interestField,
        String introduction,
        LocalDateTime createdAt,
        String profileImageUrl
) {
    public static ApplicantProfileResponseDto of(User u, String profileImageUrl) {
        return new ApplicantProfileResponseDto(
                u.getId(),
                u.getName(),
                u.getNickname(),
                u.getEmail(),
                u.getRegion().name(),
                u.getInterestField().name(),
                u.getIntroduction(),
                u.getCreatedAt(),
                profileImageUrl
        );
    }
}

