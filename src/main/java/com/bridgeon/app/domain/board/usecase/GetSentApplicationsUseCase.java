package com.bridgeon.app.domain.board.usecase;

import com.bridgeon.app.domain.board.dto.response.SentApplicationItemResponseDto;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetSentApplicationsUseCase {
    Page<SentApplicationItemResponseDto> execute(
            Long currentUserId,
            ApplyStatus status,
            Pageable pageable
    );
}
