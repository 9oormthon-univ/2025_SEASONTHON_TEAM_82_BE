package com.bridgeon.app.domain.news.service;

import com.bridgeon.app.domain.news.entity.NewsArticle;
import com.bridgeon.app.domain.news.repository.NewsArticleRepository;
import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.naver.config.TrendQueryProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

/**
 * 네이버 뉴스 크롤링 서비스 (RestTemplate)
 * - 지정된 날짜 + 카테고리 수집
 * - 조기 종료(early break) = 페이지의 '가장 최신 기사(pubDate max)'가 dayStart 이전일 때만
 * - 저장은 '지정 날짜' 기사만 (pubKst.toLocalDate().isEqual(date))
 * - 초광범위 키워드 스킵
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlService {

    private final NewsArticleRepository newsArticleRepository;
    private final TrendQueryProperties trendQueryProperties;
    private final RestTemplate restTemplate;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/news.json";
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    /** 너무 넓은 쿼리는 OpenAPI 1000 인덱스 벽에 걸리기 쉬우므로 스킵 */
    private static final Set<String> TOO_BROAD_QUERIES = Set.of("투자");

    @Transactional
    public Result crawlForDate(LocalDate date, InterestField category) {
        List<String> queries = trendQueryProperties.getQueries()
                .getOrDefault(category.name(), Collections.singletonList("투자"));

        final LocalDateTime dayStart = date.atStartOfDay();

        int inserted = 0;
        int skipped = 0;
        Set<String> seenLinks = new HashSet<>();

        for (String q : queries) {

            if (TOO_BROAD_QUERIES.contains(q)) {
                log.info("[NAVER] skip too-broad query='{}'", q);
                continue;
            }

            int startIdx = 1;
            final int display = 100;

            while (true) {
                List<NaverNewsItem> items = callNaver(q, startIdx, display);
                if (items.isEmpty()) break;

                // 이 페이지에서 가장 '최신' 기사 시간을 추적 (조기 종료 판단에 사용)
                LocalDateTime pageMaxPub = null;

                for (NaverNewsItem it : items) {
                    LocalDateTime pubKst = NaverDate.parseToKst(it.pubDate());
                    if (pubKst == null) continue;

                    if (pageMaxPub == null || pubKst.isAfter(pageMaxPub)) {
                        pageMaxPub = pubKst;
                    }

                    // 지정 날짜만 저장
                    LocalDate d = pubKst.toLocalDate();
                    if (d.isBefore(date.minusDays(360)) || d.isAfter(date)) {
                        continue;
                    }

                    String link = it.link();
                    if (link == null || link.isBlank()) continue;
                    if (!seenLinks.add(link)) continue; // 실행 내 중복 제거

                    if (newsArticleRepository.existsByNewsUrl(link)) {
                        skipped++;
                        continue;
                    }

                    NewsArticle entity = NewsArticle.builder()
                            .category(category)
                            .newstitle(clean(it.title()))
                            .originName(originOf(it))
                            .summary(clean(it.description()))
                            .newsUrl(link)
                            .pubDatetime(pubKst)
                            .source("NAVER")
                            .build();

                    newsArticleRepository.save(entity);
                    inserted++;
                }

                log.info("[NAVER] pageMaxPub={}, dayStart={}, startIdx={}", pageMaxPub, dayStart, startIdx);

                // ✅ 조기 종료: 이 페이지의 '최신' 기사조차 dayStart 이전이면 이후는 더 오래된 기사뿐
                if (pageMaxPub != null && pageMaxPub.isBefore(dayStart)) {
                    log.info("[NAVER] early-break (pageMaxPub < dayStart) -> stop paging for query='{}'", q);
                    break;
                }

                // 다음 페이지로 진행
                if (items.size() < display) {
                    log.info("[NAVER] last page reached for query='{}'", q);
                    break;
                }
                startIdx += display;

                // OpenAPI 한계(>1000 금지)
                if (startIdx > 1000) {
                    log.info("[NAVER] stop paging because startIdx(={}) > 1000 for query='{}'", startIdx, q);
                    break;
                }
                log.info("[NAVER] next page startIdx={} for query='{}'", startIdx, q);
            }
        }

        return new Result(date, category, inserted, skipped);
    }

    /** 네이버 뉴스 API 1회 호출 */
    private List<NaverNewsItem> callNaver(String query, int start, int display) {
        try {
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = NAVER_API_URL + "?query=" + encoded + "&sort=date&display=" + display + "&start=" + start;

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", clientId);
            headers.set("X-Naver-Client-Secret", clientSecret);
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (compatible; BridgeOnHack/1.0)");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<NaverNewsResponse> res =
                    restTemplate.exchange(url, HttpMethod.GET, entity, NaverNewsResponse.class);

            int size = (res.getBody() == null || res.getBody().items() == null)
                    ? 0 : res.getBody().items().size();

            log.info("[NAVER] status={}, query='{}', start={}, display={}, items={}",
                    res.getStatusCodeValue(), query, start, display, size);

            if (res.getStatusCode().is2xxSuccessful() && size > 0) {
                return res.getBody().items();
            }
        } catch (Exception e) {
            log.warn("[NAVER] call failed query='{}', start={}, display={}, err={}", query, start, display, e.toString());
        }
        return Collections.emptyList();
    }

    /** 간단한 HTML 태그/엔티티 제거 */
    private String clean(String s) {
        if (s == null) return null;
        return s.replaceAll("<[^>]*>", "")
                .replace("&quot;", "\"")
                .replace("&apos;", "'")
                .replace("&amp;", "&")
                .trim();
    }

    /** 언론사명 추출 (originallink 우선) */
    private String originOf(NaverNewsItem item) {
        try {
            String raw = item.originallink() != null ? item.originallink() : item.link();
            String host = java.net.URI.create(raw).getHost();
            return (host == null || host.isBlank()) ? "언론사" : host;
        } catch (Exception e) {
            return "언론사";
        }
    }

    /* === 내부 전용 레코드 DTO & 유틸 === */

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    private record NaverNewsResponse(List<NaverNewsItem> items) {}

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    private record NaverNewsItem(String title, String originallink, String link, String description, String pubDate) {}

    private static final class NaverDate {
        private static final java.time.format.DateTimeFormatter FMT =
                java.time.format.DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", java.util.Locale.ENGLISH);
        private static LocalDateTime parseToKst(String pubDate) {
            try {
                return ZonedDateTime.parse(pubDate, FMT).withZoneSameInstant(KST).toLocalDateTime();
            } catch (Exception e) {
                return null;
            }
        }
    }

    /** 유즈케이스에 반환할 결과 요약 */
    public record Result(LocalDate date, InterestField category, int inserted, int skipped) {}
}