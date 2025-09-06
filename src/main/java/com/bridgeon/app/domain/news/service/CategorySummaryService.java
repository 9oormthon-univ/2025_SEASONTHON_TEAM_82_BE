package com.bridgeon.app.domain.news.service;

import com.bridgeon.app.domain.news.dto.response.CategorySummaryResponseDto;
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
import java.util.List;

/** 카테고리 요약(기사 수/투자 기사 수/증감률/키워드 Top3) */
@Service
@RequiredArgsConstructor
public class CategorySummaryService {

    private final NewsArticleRepository newsArticleRepository;
    private final KeywordService keywordService; // 키워드 추출 전담(간단 유틸)

    public CategorySummaryResponseDto get(InterestField category, LocalDate date) {
        LocalDateTime s = date.atStartOfDay();
        LocalDateTime e = date.atTime(23, 59, 59);

        long today = newsArticleRepository.countByCategoryAndPubDatetimeBetween(category, s, e);

        // 샘플 최대 500개에서 투자 관련 키워드 포함 여부로 집계
        List<NewsArticle> sample = newsArticleRepository
                .findByCategoryAndPubDatetimeBetween(category, s, e, PageRequest.of(0, 500))
                .getContent();

        long invest = sample.stream()
                .filter(a -> keywordService.containsAny(
                        a.getNewstitle() + " " + (a.getSummary() == null ? "" : a.getSummary()),
                        List.of("투자","유치","라운드","시리즈","펀딩","시드")))
                .count();

        LocalDate yd = date.minusDays(1);
        long yesterday = newsArticleRepository
                .countByCategoryAndPubDatetimeBetween(category, yd.atStartOfDay(), yd.atTime(23, 59, 59));

        BigDecimal growth = (yesterday == 0) ? null :
                BigDecimal.valueOf((today - yesterday) * 100.0 / yesterday)
                        .setScale(1, RoundingMode.HALF_UP);

        List<String> top3 = keywordService.extractTopKeywords(sample, 3);

        return CategorySummaryResponseDto.of(date, category, today, invest, growth, top3);
    }
}
