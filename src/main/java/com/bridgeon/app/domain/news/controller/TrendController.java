package com.bridgeon.app.domain.news.controller;

import com.bridgeon.app.domain.news.dto.response.CategorySummaryResponseDto;
import com.bridgeon.app.domain.news.dto.response.HomeReportResponseDto;
import com.bridgeon.app.domain.news.dto.response.NewsArticleItemResponseDto;
import com.bridgeon.app.domain.news.service.ArticleListService;
import com.bridgeon.app.domain.news.service.CategorySummaryService;
import com.bridgeon.app.domain.news.service.CrawlService;
import com.bridgeon.app.domain.news.service.HomeReportService;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.enums.user.InterestField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/trends")
@RequiredArgsConstructor
public class TrendController {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final HomeReportService homeReportService;
    private final CategorySummaryService categorySummaryService;
    private final ArticleListService articleListService;
    private final CrawlService crawlService;

    /** 날짜 파라미터 없으면 KST 오늘 날짜로 기본값 설정 */
    private LocalDate resolveDate(LocalDate date) {
        return (date != null) ? date : LocalDate.now(KST);
    }

    /* =========================
     * 1) 홈 리포트
     * ========================= */
    @GetMapping("/home")
    public ResponseDto<HomeReportResponseDto> getHomeReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate target = resolveDate(date);
        HomeReportResponseDto dto = homeReportService.get(target);
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "홈 리포트 불러오기 성공",
                dto
        );
    }

    /* =========================
     * 2) 카테고리 요약
     * ========================= */
    @GetMapping("/categories/{category}/summary")
    public ResponseDto<CategorySummaryResponseDto> getCategorySummary(
            @PathVariable InterestField category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate target = resolveDate(date);
        CategorySummaryResponseDto dto = categorySummaryService.get(category, target);
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "카테고리 요약 정보 불러오기 성공",
                dto
        );
    }

    /* =========================
     * 3) 카테고리 기사 목록 (페이징)
     * ========================= */
    @GetMapping("/categories/{category}/articles")
    public ResponseDto<Page<NewsArticleItemResponseDto>> getArticles(
            @PathVariable InterestField category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable
    ) {
        LocalDate target = resolveDate(date);
        Page<NewsArticleItemResponseDto> page = articleListService.list(category, target, pageable);
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "카테고리 기사 목록 불러오기 성공",
                page
        );
    }

    /* =========================
     * 4-1) 단일 카테고리 크롤링
     *  - 기본값: 어제 날짜(매일 00시 적재를 가정)
     * ========================= */
    @PostMapping("/crawl/{category}")
    public ResponseDto<Map<String, Object>> crawlOne(
            @PathVariable InterestField category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        // 별도 지정이 없으면 어제로 수집(자정 배치 시나리오)
        LocalDate target = (date != null) ? date : LocalDate.now(KST).minusDays(1);
        log.info("아무말: {}", target);
        CrawlService.Result r = crawlService.crawlForDate(target, category);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("date", target.toString());
        body.put("category", category.name());
        body.put("inserted", r.inserted());
        body.put("skipped", r.skipped());
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "카테고리 기사 목록 불러오기 성공",
                body
        );
    }

    /* =========================
     * 4-2) 모든 카테고리 크롤링
     *  - 기본값: 어제 날짜
     * ========================= */
    @PostMapping("/crawl/all")
    public ResponseDto<Map<String, Object>> crawlAll(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate target = (date != null) ? date : LocalDate.now(KST).minusDays(1);

        int inserted = 0;
        int skipped = 0;
        List<String> categories = new ArrayList<>();

        for (InterestField c : InterestField.values()) {
            CrawlService.Result r = crawlService.crawlForDate(target, c);
            inserted += r.inserted();
            skipped += r.skipped();
            categories.add(c.name());
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("date", target.toString());
        body.put("categories", categories);
        body.put("inserted", inserted);
        body.put("skipped", skipped);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "카테고리 기사 목록 불러오기 성공",
                body
        );
    }
}
