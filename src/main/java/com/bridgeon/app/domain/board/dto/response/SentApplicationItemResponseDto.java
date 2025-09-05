package com.bridgeon.app.domain.board.dto.response;

import com.bridgeon.app.domain.board.entity.EmployApplication;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.enums.user.Region;

import java.time.LocalDateTime;

public record SentApplicationItemResponseDto(
        Long applicationId,
        Long employBoardId,
        String title,
        Region region,
        InterestField employType,
        ApplyStatus applyStatus,
        LocalDateTime createdAt
) {
    public static SentApplicationItemResponseDto of(EmployApplication a) {
        return new SentApplicationItemResponseDto(
                a.getId(),
                a.getEmployBoard().getId(),
                a.getEmployBoard().getEmployTitle(),
                a.getEmployBoard().getRegion(),
                a.getEmployBoard().getEmployType(),
                a.getApplyStatus(),
                a.getCreatedAt()
        );
    }
}
