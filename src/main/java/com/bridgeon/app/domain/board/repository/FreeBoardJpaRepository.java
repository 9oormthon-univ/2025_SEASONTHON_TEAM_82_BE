package com.bridgeon.app.domain.board.repository;

import com.bridgeon.app.domain.board.entity.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreeBoardJpaRepository extends JpaRepository<FreeBoard, Long> {
    Optional<FreeBoard> findByIdAndDeletedAtIsNull(Long id);
}
