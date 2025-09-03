package com.bridgeon.app.domain.info.service;

import com.bridgeon.app.domain.info.dto.response.FoundationInfoRecommendResponseDto;
import com.bridgeon.app.domain.info.repository.FoundationInfoJpaRepository;
import com.bridgeon.app.domain.info.usecase.FoundationInfoRecommendUseCase;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.InfoErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoundationInfoRecommendService implements FoundationInfoRecommendUseCase {

    private final FoundationInfoJpaRepository foundationInfoJpaRepository;

    @Override
    public Page<FoundationInfoRecommendResponseDto> getRecommendList(Pageable pageable) {
        Page<FoundationInfoRecommendResponseDto> page =
                foundationInfoJpaRepository.findAllByOrderByViewDesc(pageable)
                        .map(FoundationInfoRecommendResponseDto::from);

        if (page.isEmpty()) {
            throw new BusinessException(InfoErrorCode.FOUNDATION_INFO_NOT_FOUND);
        }
        return page;
    }

}
