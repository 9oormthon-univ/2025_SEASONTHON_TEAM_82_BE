package com.bridgeon.app.domain.info.service;

import com.bridgeon.app.domain.info.dto.response.FoundationInfoItemResponseDto;
import com.bridgeon.app.domain.info.repository.FoundationInfoJpaRepository;
import com.bridgeon.app.domain.info.usecase.FoundationInfoListUseCase;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.InfoErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoundationInfoListService implements FoundationInfoListUseCase {

    private final FoundationInfoJpaRepository foundationInfoJpaRepository;

    @Override
    public Page<FoundationInfoItemResponseDto> getList(Pageable pageable) {
        Page<FoundationInfoItemResponseDto> page = foundationInfoJpaRepository
                .findAll(pageable)
                .map(FoundationInfoItemResponseDto::from);

        // 결과가 비어 있으면 BusinessException 발생
        if (page.isEmpty()) {
            throw new BusinessException(InfoErrorCode.FOUNDATION_INFO_NOT_FOUND);
        }

        return page;
    }
}
