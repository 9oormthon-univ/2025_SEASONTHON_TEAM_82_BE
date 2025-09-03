package com.bridgeon.app.domain.info.dto.response;

import com.bridgeon.app.domain.info.entity.FoundationInfo;

import java.time.LocalDateTime;

public record FoundationInfoItemResponseDto(
        Long foundationInfoId,
        String title,
        String subtitle,
        String imageUrl,
        LocalDateTime recruitStartAt,
        LocalDateTime recruitEndAt,
        String infoLink
) {
    public static FoundationInfoItemResponseDto from(FoundationInfo i ) {
        return new FoundationInfoItemResponseDto(
                i.getId(),
                i.getInfoTitle(),
                i.getInfoSubtitle(),
                i.getInfoImageUrl(),
                i.getInfoStartDate(),
                i.getInfoEndDate(),
                i.getInfoLink()
        );
    }
}
