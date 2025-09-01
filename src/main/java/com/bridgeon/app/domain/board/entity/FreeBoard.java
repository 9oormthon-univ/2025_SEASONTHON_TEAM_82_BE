package com.bridgeon.app.domain.board.entity;

import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "free_boards")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_id")
    private Long id; // 자유게시판 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자

    @Column(name = "free_board_title", nullable = false, length = 200)
    private String freeBoardTitle; // 자유게시판 글 제목

    @Column(name = "free_board_content", nullable = false, columnDefinition = "TEXT")
    private String freeBoardContent; // 자유게시판 글 내용

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 자유게시판 글 삭제일
}
