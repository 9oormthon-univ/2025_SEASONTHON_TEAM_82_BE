package com.bridgeon.app.domain.info.usecase;

import com.bridgeon.app.domain.info.dto.response.FoundationInfoItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoundationInfoListUseCase {
    Page<FoundationInfoItemResponseDto> getList(Pageable pageable);
}
