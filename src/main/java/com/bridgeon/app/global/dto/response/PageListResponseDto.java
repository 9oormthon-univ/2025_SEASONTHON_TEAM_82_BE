package com.bridgeon.app.global.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageListResponseDto<T>(
        List<T> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrev
) {
    public static <T> PageListResponseDto<T> of(Page<T> page) {
        return new PageListResponseDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
