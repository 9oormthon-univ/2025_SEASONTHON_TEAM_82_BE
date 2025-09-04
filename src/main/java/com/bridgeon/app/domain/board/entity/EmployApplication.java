package com.bridgeon.app.domain.board.entity;

import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.entity.BaseEntity;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "employ_applications",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_employ_board_applicant",
                columnNames = {"employ_board_id", "apply_id"}
        )
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id; // 지원 신청 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_board_id", nullable = false)
    private EmployBoard employBoard; // 모집 글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id", nullable = false)
    private User applicant; // 지원자

    @Enumerated(EnumType.STRING)
    @Column(name = "apply_status", nullable = false, length = 20)
    private ApplyStatus applyStatus; // 신청 상태 (PENDING, APPROVED, REJECTED)

    @Column(name = "decided_at")
    private LocalDateTime decidedAt; // 승인/거절 시각

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 삭제시간
}