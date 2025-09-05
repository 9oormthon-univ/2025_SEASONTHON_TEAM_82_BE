package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.board.dto.response.ReceivedApplicationItemResponseDto;
import com.bridgeon.app.domain.board.entity.EmployApplication;
import com.bridgeon.app.domain.board.entity.EmployBoard;
import com.bridgeon.app.domain.board.repository.EmployApplicationJpaRepository;
import com.bridgeon.app.domain.board.repository.EmployBoardJpaRepository;
import com.bridgeon.app.domain.board.usecase.GetReceivedApplicationsUseCase;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.bridgeon.app.global.exception.error.EmployErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReceivedApplicationService implements GetReceivedApplicationsUseCase {

    private final EmployBoardJpaRepository employBoardJpaRepository;
    private final EmployApplicationJpaRepository employApplicationJpaRepository;


    @Override
    public Page<ReceivedApplicationItemResponseDto> execute(
            Long employBoardId,
            ApplyStatus status,
            Pageable pageable,
            Long currentUserId
    ) {
        return getReceivedApplication(employBoardId, status, pageable, currentUserId);
    }

    //받은 지원 목록 조회
    @Transactional(readOnly = true)
    public Page<ReceivedApplicationItemResponseDto> getReceivedApplication(
            Long employBoardId,
            ApplyStatus status,
            Pageable pageable,
            Long currentUserId
    ) {
        // 구인게시판 글 존재 여부 확인
        EmployBoard board = employBoardJpaRepository.findById(employBoardId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.EMPLOY_BOARD_NOT_FOUND));

        if (currentUserId == null || !board.getUser().getId().equals(currentUserId)) {
            throw new BusinessException(AuthErrorCode.ACCESS_DENIED);
        }


        // 기본 정렬 보장: createdAt DESC
        Pageable ensured = pageable.getSort().isSorted()
                ? pageable
                : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        // 상태 필터 별 조회
        Page<EmployApplication> pageResult = (status == null)
                ? employApplicationJpaRepository.findByEmployBoard_Id(employBoardId, ensured)
                : employApplicationJpaRepository.findByEmployBoard_IdAndApplyStatus(employBoardId, status, ensured);

        // DTO 매핑
        return pageResult.map(ReceivedApplicationItemResponseDto::of);
    }
}
