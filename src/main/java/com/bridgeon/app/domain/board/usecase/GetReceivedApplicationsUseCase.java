package com.bridgeon.app.domain.board.usecase;

import com.bridgeon.app.domain.board.dto.response.ReceivedApplicationItemResponseDto;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetReceivedApplicationsUseCase {
    Page<ReceivedApplicationItemResponseDto> execute(
            Long employBoardId,
            ApplyStatus status,
            Pageable pageable,
            Long currentUserId
    );
}
