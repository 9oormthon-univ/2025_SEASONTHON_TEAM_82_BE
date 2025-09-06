package com.bridgeon.app.domain.user.service;

import com.bridgeon.app.domain.board.repository.EmployApplicationJpaRepository;
import com.bridgeon.app.domain.user.dto.response.BusinessPlanDetailResponseDto;
import com.bridgeon.app.domain.user.dto.response.BusinessPlanListResponseDto;
import com.bridgeon.app.domain.user.entity.BusinessPlan;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.BusinessPlanJpaRepository;
import com.bridgeon.app.domain.user.usecase.BusinessPlanUseCase;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.BusinessPlanErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessPlanServiceImpl implements BusinessPlanUseCase {

    private final BusinessPlanJpaRepository repository;
    private final EmployApplicationJpaRepository employApplicationJpaRepository;

    /** 내 사업계획서 목록 조회 */
    @Override
    public PageListResponseDto<BusinessPlanListResponseDto> getMyBusinessPlans(Long userId, Pageable pageable) {
        Page<BusinessPlan> page = repository.findByUserId(userId, pageable);
        Page<BusinessPlanListResponseDto> dtoPage = page.map(BusinessPlanListResponseDto::of);
        return PageListResponseDto.of(dtoPage);
    }

    // 사업계획서 상세 조회
    @Override
    public BusinessPlanDetailResponseDto getBusinessPlanDetail(Long businessPlanId, Long authUserId) {

        // 1) 존재 여부
        BusinessPlan bp = repository.findById(businessPlanId)
                .orElseThrow(() -> new BusinessException(BusinessPlanErrorCode.BUSINESS_PLAN_NOT_FOUND));

        // 2) 작성자 본인 → 바로 반환
        if (bp.getUser().getId().equals(authUserId)) {
            return toDetail(bp);
        }

        // 3) 작성자가 아니면 승인 여부 확인

        User authUser = User.builder()
                .id(authUserId)
                .build();

        boolean approved = employApplicationJpaRepository
                .existsByEmployBoard_BusinessPlan_IdAndApplicantAndApplyStatusAndDeletedAtIsNull(
                        businessPlanId,
                        authUser,
                        ApplyStatus.APPROVED
                );

        if (!approved) {
            throw new BusinessException(BusinessPlanErrorCode.FORBIDDEN);
        }

        return toDetail(bp);
    }

        private BusinessPlanDetailResponseDto toDetail(BusinessPlan bp) {
            return new BusinessPlanDetailResponseDto(
                    bp.getId(),
                    bp.getTitle(),
                    bp.getBusinessType(),
                    bp.getContent(),
                    bp.getCreatedAt(),
                    bp.getUpdatedAt()
            );
    }
}