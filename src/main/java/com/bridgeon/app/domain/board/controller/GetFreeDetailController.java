package com.bridgeon.app.domain.board.controller;

import com.bridgeon.app.domain.board.dto.response.FreeDetailResponseDto;
import com.bridgeon.app.domain.board.dto.response.FreePostItemResponseDto;
import com.bridgeon.app.domain.board.usecase.FreeListUseCase;
import com.bridgeon.app.domain.board.usecase.GetFreeDetailUseCase;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import com.bridgeon.app.global.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/free-boards")
public class GetFreeDetailController {

    private final GetFreeDetailUseCase getFreeDetailUseCase;
    private final FreeListUseCase freeListUseCase;

    @GetMapping
    public ResponseDto<PageListResponseDto<FreePostItemResponseDto>> getFreeBoards(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<FreePostItemResponseDto> result = freeListUseCase.getList(pageable);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "자유게시판 목록 조회 성공",
                PageListResponseDto.of(result)
        );
    }

    @GetMapping("/{freeBoardId}")
    public ResponseDto<FreeDetailResponseDto> getDetail(@PathVariable Long freeBoardId) {
        FreeDetailResponseDto result = getFreeDetailUseCase.getDetail(freeBoardId);
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "자유게시판 상세 조회 성공",
                result
        );
    }
}
