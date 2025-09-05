package com.bridgeon.app.domain.user.controller;


import com.bridgeon.app.domain.user.dto.response.ApplicantDetailResponseDto;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.usecase.GetApplicantDetailUseCase;
import com.bridgeon.app.global.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class ApplicantDetailController {

    private final GetApplicantDetailUseCase getApplicantDetailUseCase;


    //지원자 프로필 + 포토폴리오 상세 조회
    @GetMapping("/{applicantUserId}/detail")
    public ResponseDto<ApplicantDetailResponseDto> getApplicantDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicantUserId) {

        Long currentUserId = (user == null) ? null : user.getId();

        ApplicantDetailResponseDto dto =
                getApplicantDetailUseCase.excute(applicantUserId, currentUserId);

        return ResponseDto.success(
                HttpStatus.OK,
                "지원자 상세 조회 성공",
                dto
        );
    }
}
