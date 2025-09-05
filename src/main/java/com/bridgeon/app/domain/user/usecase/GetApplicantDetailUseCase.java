package com.bridgeon.app.domain.user.usecase;

import com.bridgeon.app.domain.user.dto.response.ApplicantDetailResponseDto;

public interface GetApplicantDetailUseCase {
    ApplicantDetailResponseDto excute(Long  applicantUserId, Long currentUserId);
}
