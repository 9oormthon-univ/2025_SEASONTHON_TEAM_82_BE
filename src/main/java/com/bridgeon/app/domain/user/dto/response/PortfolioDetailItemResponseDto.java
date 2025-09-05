package com.bridgeon.app.domain.user.dto.response;

import com.bridgeon.app.domain.user.entity.Portfolio;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.enums.portfolio.Visibility;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PortfolioDetailItemResponseDto(
        Long portfolioId,
        String title,
        LocalDate periodStart,
        LocalDate periodEnd,
        String introduction,
        String fileUrl,       // 파일·첨부는 추후 Attachments 연동 시 매핑
        Visibility visibility,
        LocalDateTime createdAt
) {
    public static PortfolioDetailItemResponseDto of(Portfolio p) {
        return new PortfolioDetailItemResponseDto(
                p.getId(),
                p.getTitle(),
                p.getStartDate() != null ? p.getStartDate().toLocalDate() : null,
                p.getEndDate() != null ? p.getEndDate().toLocalDate() : null,
                p.getIntroduction(),
                null, // fileUrl은 Attachments 구현 시 채울 예정
                p.getVisibility(),
                p.getCreatedAt()
        );
    }
}

