package com.bridgeon.app.domain.user.entity;

import com.bridgeon.app.global.entity.BaseEntity;
import com.bridgeon.app.global.enums.portfolio.Visibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "portfolios")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "visibility", nullable = false)
    private Visibility visibility; // 공개 여부

    @Column(name = "title", nullable = false)
    private String title; // 프로젝트 제목

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction; // 프로젝트 소개

    @Column(name = "startDate")
    private LocalDateTime startDate; // 시작일

    @Column(name = "endDate")
    private LocalDateTime endDate; // 종료일

    @Column(name = "count_files", nullable = false)
    private Integer countFiles = 0; // 업로드된 파일/이미지 수
}
