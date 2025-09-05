package com.bridgeon.app.domain.info.dto.response;

import com.bridgeon.app.domain.info.entity.FoundationInfo;

import java.time.LocalDateTime;

public record FoundationInfoRecommendResponseDto(
        Long foundationInfoId,
        String title,
        String subtitle,
        String infoImageUrl,
        LocalDateTime recruitStartAt,
        LocalDateTime recruitEndAt,
        String infoLink,
        Integer view
) {
    public static FoundationInfoRecommendResponseDto from(FoundationInfo e) {
        return new FoundationInfoRecommendResponseDto(
                e.getId(),
                e.getInfoTitle(),
                e.getInfoSubtitle(),
                e.getInfoImageUrl(),
                e.getInfoStartDate(),
                e.getInfoEndDate(),
                e.getInfoLink(),
                e.getView()
        );
    }
}
