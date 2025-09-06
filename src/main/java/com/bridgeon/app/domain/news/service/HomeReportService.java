package com.bridgeon.app.domain.news.service;

import com.bridgeon.app.domain.news.dto.response.HomeReportResponseDto;
import com.bridgeon.app.domain.news.dto.response.NewsArticleItemResponseDto;
import com.bridgeon.app.domain.news.entity.NewsArticle;
import com.bridgeon.app.domain.news.repository.NewsArticleRepository;
import com.bridgeon.app.global.enums.user.InterestField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 홈 리포트 서비스 (해커톤용 최소본)
 * - 전체 기사 수
 * - 전일 대비 증감률
 * - 키워드 Top3
 * - 하이라이트 기사(최신 3건)
 */
@Service
@RequiredArgsConstructor
public class HomeReportService {

    private final NewsArticleRepository newsArticleRepository;
    private final KeywordService keywordService; // 간단 키워드 추출 유틸

    public HomeReportResponseDto get(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = date.atTime(23, 59, 59);

        long todayTotal = 0L;
        long yesterdayTotal = 0L;
        List<NewsArticle> pool = new ArrayList<>();

        // 오늘 전체 기사 수 집계 + 하이라이트 후보 모으기
        for (InterestField c : InterestField.values()) {
            todayTotal += newsArticleRepository.countByCategoryAndPubDatetimeBetween(c, start, end);

            pool.addAll(
                    newsArticleRepository
                            .findByCategoryAndPubDatetimeBetween(c, start, end, PageRequest.of(0, 10))
                            .getContent()
            );

            LocalDate yd = date.minusDays(1);
            yesterdayTotal += newsArticleRepository.countByCategoryAndPubDatetimeBetween(
                    c, yd.atStartOfDay(), yd.atTime(23, 59, 59));
        }

        // 전일 대비 증감률 (어제 0이면 null)
        BigDecimal growth = (yesterdayTotal == 0)
                ? null
                : BigDecimal.valueOf((todayTotal - yesterdayTotal) * 100.0 / yesterdayTotal)
                .setScale(1, RoundingMode.HALF_UP);

        // 하이라이트: 최신순 상위 3개
        pool.sort(Comparator.comparing(NewsArticle::getPubDatetime).reversed());
        List<NewsArticleItemResponseDto> highlights = pool.stream()
                .limit(3)
                .map(NewsArticleItemResponseDto::from)
                .toList();

        // 키워드 Top3
        List<String> keywords = keywordService.extractTopKeywords(pool, 3);

        return HomeReportResponseDto.of(date, todayTotal, growth, keywords, highlights);
    }
}
