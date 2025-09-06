package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.attachment.dto.response.AttachmentResponseDto;
import com.bridgeon.app.domain.attachment.repository.AttachmentJpaRepository;
import com.bridgeon.app.domain.board.dto.response.EmployPostItemResponseDto;
import com.bridgeon.app.domain.board.entity.EmployBoard;
import com.bridgeon.app.domain.board.repository.EmployBoardJpaRepository;
import com.bridgeon.app.domain.board.repository.EmployScrapJpaRepository;
import com.bridgeon.app.domain.board.usecase.CheckReadPermissionUseCase;
import com.bridgeon.app.global.enums.attachment.TargetType;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.EmployErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployDetailService {

    private final EmployBoardJpaRepository employBoardJpaRepository;
    private final EmployScrapJpaRepository employScrapJpaRepository;
    private final CheckReadPermissionUseCase checkReadPermissionUseCase;
    private final AttachmentJpaRepository attachmentJpaRepository;

    @Transactional(readOnly = true)
    public EmployPostItemResponseDto getDetail(Long employBoardId, Long currentUserId) {
        EmployBoard board = employBoardJpaRepository.findById(employBoardId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.EMPLOY_BOARD_NOT_FOUND));

        boolean isOwner = currentUserId != null && board.getUser().getId().equals(currentUserId);

        Long businessPlanId = (board.getBusinessPlan() != null)
                ? board.getBusinessPlan().getId()
                : null;

        boolean authorized = isOwner
                || (currentUserId != null
                && businessPlanId != null
                && checkReadPermissionUseCase.canReadBusinessPlan(businessPlanId, currentUserId));

        boolean isScraped = currentUserId != null
                && employScrapJpaRepository.existsByUserIdAndEmployBoardId(currentUserId, board.getId());

        List<AttachmentResponseDto> attachments = AttachmentResponseDto.ofList(
                attachmentJpaRepository.findByTargetTypeAndTargetIdOrderBySortOrderAsc(
                        TargetType.EMPLOY_BOARD, employBoardId
                )
        );

        return EmployPostItemResponseDto.of(board, authorized, isScraped, attachments);
    }
}
