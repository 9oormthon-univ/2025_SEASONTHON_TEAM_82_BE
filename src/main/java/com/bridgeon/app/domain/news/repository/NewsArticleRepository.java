package com.bridgeon.app.domain.news.repository;

import com.bridgeon.app.domain.news.entity.NewsArticle;
import com.bridgeon.app.global.enums.user.InterestField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


/**
 * 뉴스 아티클 레포지토리
 * - JPA 기반 기본 CRUD
 * - 중복 방지 및 날짜 범위 조회용 메소드 정의
 */
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

    //특정 뉴스 URL이 이미 DB에 존재하는지 여부
    boolean existsByNewsUrl(String newsUrl);

    //특정 카테고리 + 날짜 범위 내 기사 개수
    long countByCategoryAndPubDatetimeBetween(
            InterestField category,
            LocalDateTime start,
            LocalDateTime end
    );

    //특정 카테고리 + 날짜 범위 내 기사 목록 (페이징 지원)
    Page<NewsArticle> findByCategoryAndPubDatetimeBetween(
            InterestField category,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}
