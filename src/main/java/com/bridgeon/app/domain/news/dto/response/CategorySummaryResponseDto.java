package com.bridgeon.app.domain.news.dto.response;


import com.bridgeon.app.global.enums.user.InterestField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 카테고리 요약 응답 DTO
 */
public record CategorySummaryResponseDto(
        LocalDate date,
        InterestField category,
        long totalCount,
        long investmentCount,
        BigDecimal growthPct,
        List<String> topKeywords
) {
    public static CategorySummaryResponseDto of(
            LocalDate date,
            InterestField category,
            long totalCount,
            long investmentCount,
            BigDecimal growthPct,
            List<String> topKeywords
    ) {
        return new CategorySummaryResponseDto(
                date, category, totalCount, investmentCount, growthPct, topKeywords
        );
    }
}
