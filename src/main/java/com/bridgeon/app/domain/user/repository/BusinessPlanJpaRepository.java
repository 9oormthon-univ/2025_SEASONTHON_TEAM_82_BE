package com.bridgeon.app.domain.user.repository;

import com.bridgeon.app.domain.user.entity.BusinessPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessPlanJpaRepository extends JpaRepository<BusinessPlan, Long> {

    //내 목록 조회
    Page<BusinessPlan> findByUserId(Long userId, Pageable pageable);

    //상세 + 소유자 검증
    Optional<BusinessPlan> findByIdAndUserId(Long businessPlanId, Long userId);
}
