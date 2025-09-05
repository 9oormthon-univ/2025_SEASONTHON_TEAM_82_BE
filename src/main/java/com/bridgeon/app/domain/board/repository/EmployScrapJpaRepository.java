package com.bridgeon.app.domain.board.repository;

import com.bridgeon.app.domain.board.entity.EmployScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployScrapJpaRepository extends JpaRepository<EmployScrap, Long> {
    boolean existsByUserIdAndEmployBoardId(Long userId, Long employBoardId);
}