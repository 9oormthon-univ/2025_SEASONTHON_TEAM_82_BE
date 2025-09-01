package com.bridgeon.app.domain.user.entity;

import com.bridgeon.app.global.entity.BaseEntity;
import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.enums.user.Provider;
import com.bridgeon.app.global.enums.user.Region;
import com.bridgeon.app.global.enums.user.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider; // 소셜로그인 제공자

    @Enumerated(EnumType.STRING)
    @Column(name = "role",  nullable = false)
    private Role role; // 사용자 권한

    @Column(name = "email", nullable = false)
    private String email; // 소셜로그인 이메일

    @Column(name = "name", nullable = false)
    private String name; // 소셜로그인 이름

    @Column(name = "nickname", nullable = false)
    private String nickname; // 닉네임

    @Column(name = "region")
    private Region region; // 지역

    @Column(name = "interest_field")
    private InterestField interestField; // 창업 관심 분야

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction; // 자기소개

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 탈퇴일
}
