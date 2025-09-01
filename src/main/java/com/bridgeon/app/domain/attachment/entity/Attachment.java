package com.bridgeon.app.domain.attachment.entity;

import com.bridgeon.app.global.enums.attachment.MimeType;
import com.bridgeon.app.global.enums.attachment.TargetType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachments_id")
    private Long id; // 첨부파일 ID

    @Column(name = "file_url", nullable = false, columnDefinition = "TEXT")
    private String fileUrl;  // 파일 저장 경로

    @Enumerated(EnumType.STRING)
    @Column(name = "mime_type", nullable = false)
    private MimeType mimeType; // 파일 타입(PDF/PNG 등)

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;  // 파일 연결 대상 종류

    @Column(name = "target_id", nullable = false)
    private Long targetId; // 연결된 대상 PK 값

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;  // 정렬 순서(기본 0)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 생성일

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (sortOrder == null) sortOrder = 0;
    }
}
