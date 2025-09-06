package com.bridgeon.app.domain.user.dto.response;

import com.bridgeon.app.domain.user.entity.BusinessPlan;
import com.bridgeon.app.global.dto.json.businessplan.Content;
import com.bridgeon.app.global.enums.user.InterestField;

import java.time.LocalDateTime;

public record BusinessPlanDetailResponseDto(
        Long businessPlanId,
        String title,
        InterestField businessType,
        Content content, //{ overview, marketAnalysis, businessModel, actionPlan }
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BusinessPlanDetailResponseDto of(BusinessPlan bp) {
        return new BusinessPlanDetailResponseDto(
                bp.getId(),
                bp.getTitle(),
                bp.getBusinessType(),
                bp.getContent(),
                bp.getCreatedAt(),
                bp.getUpdatedAt()
        );
    }
}
