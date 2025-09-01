package com.bridgeon.app.domain.board.repositroy;

import com.bridgeon.app.domain.board.entity.EmployScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployScrapJpaRepository extends JpaRepository<EmployScrap, Long> {
    boolean existsByUserIdAndEmployBoardId(Long userId, Long employBoardId);
}