package com.bridgeon.app.domain.board.entity;

import com.bridgeon.app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "free_scraps",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_free_scrap_user_board",
                        columnNames = {"user_id", "free_board_id"}
                )
        }
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_scrap_id")
    private Long id; // 자유게시판 스크랩 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 스크랩한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id", nullable = false)
    private FreeBoard freeBoard; // 스크랩된 자유게시판 글

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성일
}