package com.bridgeon.app.domain.chat.entity;

import com.bridgeon.app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator; // 채팅 생성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private User participant; // 채팅 참여자

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 채팅 생성일

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt; // 채팅 삭제일
}
