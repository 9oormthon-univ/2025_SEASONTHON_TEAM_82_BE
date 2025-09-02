package com.bridgeon.app.domain.board.usecase;

import com.bridgeon.app.domain.board.dto.response.FreePostItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreeListUseCase {
    Page<FreePostItemResponseDto> getList(Pageable pageable);
}
