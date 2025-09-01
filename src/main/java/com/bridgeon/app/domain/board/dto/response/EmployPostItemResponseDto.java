package com.bridgeon.app.domain.board.dto.response;

import com.bridgeon.app.domain.board.entity.EmployBoard;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.enums.user.Region;

import java.time.LocalDateTime;

public record EmployPostItemResponseDto(
        Long employBoardId,
        Long userId, // 인증 구현되면 수정 할 예정
        Region region,
        InterestField employType,
        String employTitle,
        String employContent,
        LocalDateTime employStartDate,
        LocalDateTime employEndDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        boolean isScraped
) {
    public static EmployPostItemResponseDto of(EmployBoard e, boolean isScraped) {
        return new EmployPostItemResponseDto(
                e.getId(),
                e.getUser() != null ? e.getUser().getId() : null, // 인증 구현되면 수정 예정
                e.getRegion(),
                e.getEmployType(),
                e.getEmployTitle(),
                e.getEmployContent(),
                e.getEmployStartDate(),
                e.getEmployEndDate(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeletedAt(),
                isScraped
        );
    }
}