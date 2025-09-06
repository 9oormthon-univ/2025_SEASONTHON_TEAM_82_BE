package com.bridgeon.app.domain.user.usecase;

import com.bridgeon.app.domain.user.dto.response.PortfolioDetailResponseDto;
import com.bridgeon.app.domain.user.dto.response.PortfolioListResponseDto;
import com.bridgeon.app.domain.user.entity.User;

public interface PortfolioUseCase {
    PortfolioListResponseDto getListByUserId(Long userId);
    PortfolioDetailResponseDto getDetail(User viewer, Long portfolioId);
}
