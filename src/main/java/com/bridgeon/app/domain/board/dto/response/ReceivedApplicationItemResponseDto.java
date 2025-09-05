package com.bridgeon.app.domain.board.dto.response;

import com.bridgeon.app.domain.board.entity.EmployApplication;
import com.bridgeon.app.global.enums.board.ApplyStatus;

import java.time.LocalDateTime;

public record ReceivedApplicationItemResponseDto(
        Long applicationId,
        Long applyId,
        String applicantName,
        ApplyStatus applyStatus,
        LocalDateTime createdAt
) {
    public static ReceivedApplicationItemResponseDto of(EmployApplication a) {
        return new ReceivedApplicationItemResponseDto(
                a.getId(),
                a.getApplicant().getId(),
                a.getApplicant().getName(),
                a.getApplyStatus(),
                a.getCreatedAt()
        );
    }
}
