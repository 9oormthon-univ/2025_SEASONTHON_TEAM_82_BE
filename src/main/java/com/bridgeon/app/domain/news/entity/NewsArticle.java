package com.bridgeon.app.domain.news.entity;

import com.bridgeon.app.global.enums.user.InterestField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_articles")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_article_id")
    private Long id; // 뉴스 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterestField category; // 카테고리(FOOD, ECO 등)

    @Column(nullable = false, length = 300)
    private String newstitle; // 뉴스 제목

    @Column(length = 100)
    private String originName; // 언론사명

    @Column(columnDefinition = "TEXT")
    private String summary; // 뉴스 요약

    @Column(nullable = false, unique = true, length = 500)
    private String newsUrl; // 기사 링크

    @Column(nullable = false)
    private LocalDateTime pubDatetime; // 발행일시 (KST)

    @Column(nullable = false, length = 20)
    private String source; // NAVER 등
}
