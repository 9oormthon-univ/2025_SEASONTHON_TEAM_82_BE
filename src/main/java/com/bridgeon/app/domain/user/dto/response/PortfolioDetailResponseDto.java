package com.bridgeon.app.domain.user.dto.response;

import com.bridgeon.app.global.enums.portfolio.Visibility;

import java.time.LocalDateTime;

public record PortfolioDetailResponseDto(
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String introduction,
        Visibility visibility
) {
}
