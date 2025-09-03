package com.bridgeon.app.domain.info.usecase;

import com.bridgeon.app.domain.info.dto.response.FoundationInfoRecommendResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoundationInfoRecommendUseCase {
    Page<FoundationInfoRecommendResponseDto> getRecommendList(Pageable pageable);
}
