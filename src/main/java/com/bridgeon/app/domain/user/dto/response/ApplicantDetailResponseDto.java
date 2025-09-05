package com.bridgeon.app.domain.user.dto.response;

import java.util.List;

public record ApplicantDetailResponseDto(
        ApplicantProfileResponseDto profile,
        java.util.List<PortfolioDetailItemResponseDto> portfolios
) {
    public static ApplicantDetailResponseDto of(ApplicantProfileResponseDto profile, List<PortfolioDetailItemResponseDto> portfolios) {

        return new ApplicantDetailResponseDto(profile, portfolios);
    }
}
