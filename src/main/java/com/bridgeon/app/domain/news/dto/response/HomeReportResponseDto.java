package com.bridgeon.app.domain.news.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


// 홈 리포트 응답 DTO
public record HomeReportResponseDto(
        LocalDate date,
        long totalCount,
        BigDecimal overallGrowthPct,
        List<String> keywords,
        List<NewsArticleItemResponseDto> highlights
) {
    public static HomeReportResponseDto of(
            LocalDate date,
            long totalCount,
            BigDecimal overallGrowthPct,
            List<String> keywords,
            List<NewsArticleItemResponseDto> highlights
    ) {
        return new HomeReportResponseDto(date, totalCount, overallGrowthPct, keywords, highlights);
    }
}
