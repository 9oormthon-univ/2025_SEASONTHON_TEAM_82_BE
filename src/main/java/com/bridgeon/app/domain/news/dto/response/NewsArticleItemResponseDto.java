package com.bridgeon.app.domain.news.dto.response;


import com.bridgeon.app.domain.news.entity.NewsArticle;
import com.bridgeon.app.global.enums.user.InterestField;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 뉴스 기사 단건 응답 DTO
 */
public record NewsArticleItemResponseDto(
        Long newsId,
        InterestField category,
        String title,
        String originName,
        String summary,
        String newsUrl,
        LocalDateTime pubDatetime,
        String source
) {
    public static NewsArticleItemResponseDto from(NewsArticle n) {
        return new NewsArticleItemResponseDto(
                n.getId(),
                n.getCategory(),
                n.getNewstitle(),
                n.getOriginName(),
                n.getSummary(),
                n.getNewsUrl(),
                n.getPubDatetime(),
                n.getSource()
        );
    }
}
