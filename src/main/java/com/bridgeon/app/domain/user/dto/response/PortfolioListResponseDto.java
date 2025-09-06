package com.bridgeon.app.domain.user.dto.response;

import java.util.List;

public record PortfolioListResponseDto(
        List<PortfolioItemDto> portfolios
) {
}
