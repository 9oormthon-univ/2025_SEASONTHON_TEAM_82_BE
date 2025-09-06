package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.attachment.dto.response.AttachmentResponseDto;
import com.bridgeon.app.domain.attachment.repository.AttachmentJpaRepository;
import com.bridgeon.app.domain.board.dto.response.FreeDetailResponseDto;
import com.bridgeon.app.domain.board.entity.FreeBoard;
import com.bridgeon.app.domain.board.repository.FreeBoardJpaRepository;
import com.bridgeon.app.domain.board.usecase.GetFreeDetailUseCase;
import com.bridgeon.app.global.enums.attachment.TargetType;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.FreeErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeDetailService implements GetFreeDetailUseCase {

    private final FreeBoardJpaRepository freeBoardRepository;
    private final AttachmentJpaRepository attachmentRepository;


    @Transactional(readOnly = true)
    @Override
    public FreeDetailResponseDto getDetail(Long freeBoardId) {
        FreeBoard freeBoard = freeBoardRepository.findByIdAndDeletedAtIsNull(freeBoardId)
                .orElseThrow(() -> new BusinessException(FreeErrorCode.FREE_BOARD_NOT_FOUND));

        List<AttachmentResponseDto> attachments = AttachmentResponseDto.ofList(
                attachmentRepository.findByTargetTypeAndTargetIdOrderBySortOrderAsc(
                        TargetType.FREE_BOARD, freeBoardId
                )
        );

        return FreeDetailResponseDto.from(freeBoard,attachments);
    }
}
