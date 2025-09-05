package com.bridgeon.app.domain.user.entity;

import com.bridgeon.app.global.dto.json.businessplan.Content;
import com.bridgeon.app.global.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "business_plans")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_plan_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title; // 사업계획서 제목

    @Type(JsonType.class)
    @Column(name = "content", nullable = false, columnDefinition = "json")
    private Content content; // 사업계획서 내용

    @Column(name = "count_files", nullable = false)
    private Integer countFiles = 0; // 업로드 된 파일 수
}
