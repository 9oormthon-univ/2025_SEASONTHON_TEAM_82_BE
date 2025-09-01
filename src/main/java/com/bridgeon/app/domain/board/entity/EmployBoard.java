package com.bridgeon.app.domain.board.entity;

import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.entity.BaseEntity;
import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.enums.user.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "employ_boards")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employ_board_id")
    private Long id; // 구인게시판 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private Region region; // 지역 (ENUM)

    @Enumerated(EnumType.STRING)
    @Column(name = "employ_type", nullable = false)
    private InterestField employType; // 분야 (ENUM)

    @Column(name = "employ_title", nullable = false, length = 200)
    private String employTitle; // 구인게시판 글 제목

    @Column(name = "employ_content", nullable = false, columnDefinition = "TEXT")
    private String employContent; // 구인게시판 글 내용

    @Column(name = "employ_start_date", nullable = false)
    private LocalDateTime employStartDate; // 구인 모집 시작 날짜

    @Column(name = "employ_end_date", nullable = false)
    private LocalDateTime employEndDate; // 구인 모집 마감 날짜

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 구인게시판 글 삭제일
}
