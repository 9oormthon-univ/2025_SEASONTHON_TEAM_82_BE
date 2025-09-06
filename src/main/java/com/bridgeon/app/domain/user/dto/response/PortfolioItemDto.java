package com.bridgeon.app.domain.user.dto.response;

import java.time.LocalDateTime;

public record PortfolioItemDto(
        Long id,
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
