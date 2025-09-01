package com.bridgeon.app.domain.board.dto.response;

import java.util.List;

public record EmployPostListResponseDto(
        List<EmployPostItemResponseDto> employPostItemResponseDtoList,
        int page,
        int size,
        long total
) {
}
