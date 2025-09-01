package com.bridgeon.app.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "read_authorizations")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "read_authorization_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_plan_id", nullable = false)
    private BusinessPlan businessPlan;

    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted = false; // 승인 여부

    @CreatedDate
    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt; // 요청 일자

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt; // 승인 일자
}
