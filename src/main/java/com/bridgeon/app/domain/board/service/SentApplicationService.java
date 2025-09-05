package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.board.dto.response.SentApplicationItemResponseDto;
import com.bridgeon.app.domain.board.entity.EmployApplication;
import com.bridgeon.app.domain.board.repository.EmployApplicationJpaRepository;
import com.bridgeon.app.domain.board.usecase.GetReceivedApplicationsUseCase;
import com.bridgeon.app.domain.board.usecase.GetSentApplicationsUseCase;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
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
public class SentApplicationService implements GetSentApplicationsUseCase {

    private final EmployApplicationJpaRepository employApplicationJpaRepository;

    // 보낸 지원 목록 조회
    @Override
    public Page<SentApplicationItemResponseDto> execute(
            Long currentUserId,
            ApplyStatus status,
            Pageable pageable
    ) {
        if (currentUserId == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        // 기본 정렬 보장: createdAt DESC
        Pageable ensured = pageable.getSort().isSorted()
                ? pageable
                : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<EmployApplication> page = (status == null)
                ? employApplicationJpaRepository.findByApplicant_Id(currentUserId, ensured)
                : employApplicationJpaRepository.findByApplicant_IdAndApplyStatus(currentUserId, status, ensured);

        return page.map(SentApplicationItemResponseDto::of);
    }
}