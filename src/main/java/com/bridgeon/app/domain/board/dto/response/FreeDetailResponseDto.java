package com.bridgeon.app.domain.board.dto.response;

import com.bridgeon.app.domain.attachment.dto.response.AttachmentResponseDto;
import com.bridgeon.app.domain.board.entity.FreeBoard;
import com.bridgeon.app.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record FreeDetailResponseDto(
        Long freeBoardId,
        Long userId, //추후 인증구현되면 수정 예정
        String freeBoardTitle,
        String freeBoardContent,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        List<AttachmentResponseDto> attachments
) {
    public static FreeDetailResponseDto from(FreeBoard f, List<AttachmentResponseDto> attachments) {
        return new FreeDetailResponseDto(
                f.getId(),
                f.getUser() != null ? f.getUser().getId() : null, //추후에 인증 구현되면 수정 예정
                f.getFreeBoardTitle(),
                f.getFreeBoardContent(),
                f.getCreatedAt(),
                f.getUpdatedAt(),
                f.getDeletedAt(),
                attachments
        );

    }
}
