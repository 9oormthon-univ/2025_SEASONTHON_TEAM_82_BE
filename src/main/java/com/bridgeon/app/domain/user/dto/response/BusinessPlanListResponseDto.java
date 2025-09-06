package com.bridgeon.app.domain.user.dto.response;

import com.bridgeon.app.domain.user.entity.BusinessPlan;
import com.bridgeon.app.global.enums.user.InterestField;

import java.time.LocalDateTime;

public record BusinessPlanListResponseDto(
        Long businessPlanId,
        String title,
        InterestField businessType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BusinessPlanListResponseDto of(BusinessPlan bp) {
        return new BusinessPlanListResponseDto(
                bp.getId(),
                bp.getTitle(),
                bp.getBusinessType(),
                bp.getCreatedAt(),
                bp.getUpdatedAt()
        );
    }
}

