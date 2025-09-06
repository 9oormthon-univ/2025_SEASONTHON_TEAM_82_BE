package com.bridgeon.app.domain.user.controller;

import com.bridgeon.app.domain.user.dto.response.PortfolioDetailResponseDto;
import com.bridgeon.app.domain.user.dto.response.PortfolioListResponseDto;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.usecase.PortfolioUseCase;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioUseCase portfolioUseCase;

    @GetMapping("/my")
    public ResponseDto<PortfolioListResponseDto> getMyPortfolios(
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        PortfolioListResponseDto data = portfolioUseCase.getListByUserId(user.getId());

        return ResponseDto.success(
                HttpStatus.OK,
                "내 포트폴리오 목록 조회 성공",
                data
        );
    }

    @GetMapping("/{portfolioId}")
    public ResponseDto<PortfolioDetailResponseDto> getPortfolioDetail(
        @AuthenticationPrincipal User user,
        @PathVariable Long portfolioId
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        PortfolioDetailResponseDto data = portfolioUseCase.getDetail(user, portfolioId);

        return ResponseDto.success(
                HttpStatus.OK,
                "포트폴리오 상세 조회 성공",
                data
        );
    }

}
