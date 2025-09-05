package com.bridgeon.app.domain.board.entity;

import com.bridgeon.app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "employ_scraps",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_employ_scrap_user_board",
                        columnNames = {"user_id", "employ_board_id"}
                )
        }
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employ_scrap_id")
    private Long id; // 구인게시판 스크랩 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 스크랩한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_board_id", nullable = false)
    private EmployBoard employBoard; // 스크랩된 구인게시판 글

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성일
}
