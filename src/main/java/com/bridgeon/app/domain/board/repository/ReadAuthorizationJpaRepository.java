package com.bridgeon.app.domain.board.repository;

import com.bridgeon.app.domain.user.entity.ReadAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReadAuthorizationJpaRepository extends JpaRepository<ReadAuthorization, Long> {

    // 승인 여부체크
    boolean existsByBusinessPlan_IdAndUser_IdAndIsAcceptedTrue(Long businessPlanId, Long userId);

}
