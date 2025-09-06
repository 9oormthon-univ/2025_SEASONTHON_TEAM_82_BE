package com.bridgeon.app.domain.news.service;

import com.bridgeon.app.domain.news.dto.response.NewsArticleItemResponseDto;
import com.bridgeon.app.domain.news.entity.NewsArticle;
import com.bridgeon.app.domain.news.repository.NewsArticleRepository;
import com.bridgeon.app.global.enums.user.InterestField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ArticleListService {
    private final NewsArticleRepository newsArticleRepository;

    public Page<NewsArticleItemResponseDto> list(InterestField category, LocalDate date, Pageable pageable) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        Sort sort = pageable.getSort().isUnsorted()
                ? Sort.by(Sort.Direction.DESC, "pubDatetime")
                : pageable.getSort();

        Pageable pageReq = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<NewsArticle> page = newsArticleRepository
                .findByCategoryAndPubDatetimeBetween(category, start, end, pageReq);

        return page.map(NewsArticleItemResponseDto::from);
    }
}

