package com.bridgeon.app.domain.user.service;

import com.bridgeon.app.domain.user.dto.response.PortfolioDetailResponseDto;
import com.bridgeon.app.domain.user.dto.response.PortfolioItemDto;
import com.bridgeon.app.domain.user.dto.response.PortfolioListResponseDto;
import com.bridgeon.app.domain.user.entity.Portfolio;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.PortfolioJpaRepository;
import com.bridgeon.app.domain.user.usecase.PortfolioUseCase;
import com.bridgeon.app.global.enums.portfolio.Visibility;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService implements PortfolioUseCase {

    private final PortfolioJpaRepository portfolioRepository;

    @Override
    public PortfolioListResponseDto getListByUserId(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findAllByUser_IdOrderByStartDateDesc(userId);

        List<PortfolioItemDto> items = portfolios.stream()
                .map(p -> new PortfolioItemDto(
                        p.getId(),
                        p.getTitle(),
                        p.getStartDate(),
                        p.getEndDate()
                ))
                .toList();

        return new PortfolioListResponseDto(items);
    }


    @Override
    public PortfolioDetailResponseDto getDetail(User viewer, Long portfolioId) {
        Portfolio p = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.UNAUTHORIZED));

        // PRIVATE이면 소유자만 접근 가능
        if (p.getVisibility() == Visibility.PRIVATE &&
                !p.getUser().getId().equals(viewer.getId())) {
            throw new BusinessException(AuthErrorCode.ACCESS_DENIED);
        }

        return new PortfolioDetailResponseDto(
                p.getTitle(),
                p.getStartDate(),
                p.getEndDate(),
                p.getIntroduction(),
                p.getVisibility()
        );
    }
}
