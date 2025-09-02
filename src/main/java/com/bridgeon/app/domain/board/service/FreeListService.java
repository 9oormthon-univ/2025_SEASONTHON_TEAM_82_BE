package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.board.dto.response.FreePostItemResponseDto;
import com.bridgeon.app.domain.board.entity.FreeBoard;
import com.bridgeon.app.domain.board.repository.FreeBoardJpaRepository;
import com.bridgeon.app.domain.board.usecase.FreeListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeListService implements FreeListUseCase {

    private final FreeBoardJpaRepository freeBoardJpaRepository;

    @Override
    public Page<FreePostItemResponseDto> getList(Pageable pageable) {
        Page<FreeBoard> freeBoards = freeBoardJpaRepository.findAll(pageable);
        return freeBoardJpaRepository.findAll(pageable)
                .map(FreePostItemResponseDto::from);
    }
}
