package com.bridgeon.app.domain.board.usecase;

import com.bridgeon.app.domain.board.dto.response.FreeDetailResponseDto;

public interface GetFreeDetailUseCase {
    FreeDetailResponseDto getDetail(Long freeBoardId);
}
