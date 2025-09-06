package com.bridgeon.app.domain.user.usecase;

import com.bridgeon.app.domain.user.dto.response.BusinessPlanDetailResponseDto;
import com.bridgeon.app.domain.user.dto.response.BusinessPlanListResponseDto;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import org.springframework.data.domain.Pageable;

public interface BusinessPlanUseCase {

    // 내가 작성한 사업계획서 목록 조회
    PageListResponseDto<BusinessPlanListResponseDto> getMyBusinessPlans(Long userId, Pageable pageable);

    // 사업계획서 상세 조회
    BusinessPlanDetailResponseDto getBusinessPlanDetail(Long businessPlanId, Long authUserId);
}
