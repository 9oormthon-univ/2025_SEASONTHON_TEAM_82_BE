package com.bridgeon.app.domain.user.controller;


import com.bridgeon.app.domain.user.dto.response.BusinessPlanDetailResponseDto;
import com.bridgeon.app.domain.user.dto.response.BusinessPlanListResponseDto;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.usecase.BusinessPlanUseCase;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import com.bridgeon.app.global.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/business-plans")
@RequiredArgsConstructor
public class BusinessPlanController {

    private final BusinessPlanUseCase businessPlanUseCase;


    // 사업계획서 목록 조회
    @GetMapping("/my")
    public ResponseDto<PageListResponseDto<BusinessPlanListResponseDto>> getMyBusinessPlans(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 15, sort = "createdAt") Pageable pageable
    ) {
        PageListResponseDto<BusinessPlanListResponseDto> dto =
                businessPlanUseCase.getMyBusinessPlans(user.getId(), pageable);

        return ResponseDto.success(
                HttpStatus.OK,
                "내 사업계획서 목록 조회 성공",
                dto
        );
    }

    // 사업계획서 상세조회 조회
    @GetMapping("/{businessPlanId}")
    public ResponseDto<BusinessPlanDetailResponseDto> getBusinessPlanDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long businessPlanId
    ) {
        BusinessPlanDetailResponseDto dto =
                businessPlanUseCase.getBusinessPlanDetail(businessPlanId, user.getId());

        return ResponseDto.success(
                HttpStatus.OK,
                "사업계획서 상세 조회 성공",
                dto
        );
    }
}

