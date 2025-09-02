package com.bridgeon.app.domain.board.dto.response;

import com.bridgeon.app.domain.board.entity.FreeBoard;

import java.time.LocalDateTime;

public record FreePostItemResponseDto(
        Long freeboardId,
        String freeBoardTitle,
        String freeBoardContent,
        LocalDateTime createdAt
) {
    public static FreePostItemResponseDto from(FreeBoard f) {
        return new FreePostItemResponseDto(
                f.getId(),
                f.getFreeBoardTitle(),
                f.getFreeBoardContent(),
                f.getCreatedAt()
        );
    }
}
