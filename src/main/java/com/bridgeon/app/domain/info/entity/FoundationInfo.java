package com.bridgeon.app.domain.info.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "foundation_infos")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoundationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foundation_info_id",  nullable = false)
    private Long id; // 창업 정보 ID

    @Column(name = "info_title", nullable = false)
    private String infoTitle; // 창업 정보 제목

    @Column(name = "info_subtitle", columnDefinition = "TEXT")
    private String infoSubtitle; // 부제목

    @Column(name = "info_start_date", nullable = false)
    private LocalDateTime infoStartDate; // 모집 시작 날짜

    @Column(name = "info_end_date", nullable = false)
    private LocalDateTime infoEndDate; // 모집 마감 날짜

    @Column(name = "info_link", nullable = false, columnDefinition = "TEXT")
    private String infoLink; // 연결될 링크
}
