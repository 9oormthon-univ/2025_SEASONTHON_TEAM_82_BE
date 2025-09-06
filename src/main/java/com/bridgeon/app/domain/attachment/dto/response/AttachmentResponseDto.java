package com.bridgeon.app.domain.attachment.dto.response;

import com.bridgeon.app.domain.attachment.entity.Attachment;
import com.bridgeon.app.global.enums.attachment.MimeType;
import com.bridgeon.app.global.enums.attachment.TargetType;

import java.time.LocalDateTime;
import java.util.List;

public record AttachmentResponseDto(
        Long attachmentsId,
        String fileUrl,
        MimeType mimeType,
        TargetType targetType,
        Long targetId,
        Integer sortOrder,
        LocalDateTime createdAt
) {
    public static AttachmentResponseDto of(Attachment entity) {
        return new AttachmentResponseDto(
                entity.getId(),
                entity.getFileUrl(),
                entity.getMimeType(),
                entity.getTargetType(),
                entity.getTargetId(),
                entity.getSortOrder(),
                entity.getCreatedAt()
        );
    }

    public static List<AttachmentResponseDto> ofList(List<Attachment> attachments) {
        if (attachments == null || attachments.isEmpty()) {
            return List.of();
        }
        return attachments.stream()
                .map(AttachmentResponseDto::of)

                .toList();
    }
}
