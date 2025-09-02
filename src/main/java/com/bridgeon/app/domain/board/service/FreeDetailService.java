package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.board.dto.response.FreeDetailResponseDto;
import com.bridgeon.app.domain.board.entity.FreeBoard;
import com.bridgeon.app.domain.board.repositroy.FreeBoardJpaRepository;
import com.bridgeon.app.domain.board.usecase.GetFreeDetailUseCase;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.FreeErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreeDetailService implements GetFreeDetailUseCase {

    private final FreeBoardJpaRepository freeBoardRepository;

    @Transactional(readOnly = true)
    @Override
    public FreeDetailResponseDto getDetail(Long freeBoardId) {
        FreeBoard freeBoard = freeBoardRepository.findByIdAndDeletedAtIsNull(freeBoardId)
                .orElseThrow(() -> new BusinessException(FreeErrorCode.FREE_BOARD_NOT_FOUND));

        return FreeDetailResponseDto.from(freeBoard);
    }
}
