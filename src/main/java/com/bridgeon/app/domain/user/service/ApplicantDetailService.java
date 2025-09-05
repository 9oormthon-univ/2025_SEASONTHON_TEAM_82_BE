package com.bridgeon.app.domain.user.service;

import com.bridgeon.app.domain.user.dto.response.ApplicantDetailResponseDto;
import com.bridgeon.app.domain.user.dto.response.ApplicantProfileResponseDto;
import com.bridgeon.app.domain.user.dto.response.PortfolioDetailItemResponseDto;
import com.bridgeon.app.domain.user.entity.Portfolio;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.PortfolioJpaRepository;
import com.bridgeon.app.domain.user.repository.UserRepository;
import com.bridgeon.app.domain.user.usecase.GetApplicantDetailUseCase;
import com.bridgeon.app.global.enums.portfolio.Visibility;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ApplicantDetailService implements GetApplicantDetailUseCase {

    private final UserRepository userRepository;
    private final PortfolioJpaRepository portfolioJpaRepository;

    @Override
    public ApplicantDetailResponseDto excute(Long applicantUserId, Long currentUserId) {

        User applicant = userRepository.findById(applicantUserId)
                .orElseThrow(() -> new BusinessException((AuthErrorCode.USER_NOT_FOUND)));

        // 내 mypage O -> 전부 공개, 아니면 PUBLIC만 노출
        boolean isMyPage = currentUserId != null && currentUserId.equals(applicantUserId);

        List<Portfolio> portfolios = portfolioJpaRepository.findByUser_IdOrderByCreatedAtDesc(applicantUserId);

        List<PortfolioDetailItemResponseDto> portfolioDto = portfolios.stream()
                .filter(p -> isMyPage || p.getVisibility() == null || p.getVisibility() == Visibility.PUBLIC)
                .sorted(Comparator.comparing(Portfolio::getCreatedAt).reversed())
                .map(PortfolioDetailItemResponseDto::of)
                .toList();

        ApplicantProfileResponseDto applicantProfileResponseDto = ApplicantProfileResponseDto.of(applicant);

        return new ApplicantDetailResponseDto(applicantProfileResponseDto, portfolioDto);

    }

}
