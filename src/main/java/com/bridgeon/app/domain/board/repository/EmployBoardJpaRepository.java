package com.bridgeon.app.domain.board.repository;

import com.bridgeon.app.domain.board.entity.EmployBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployBoardJpaRepository extends JpaRepository<EmployBoard, Long> {
    // 목록 조회
    Page<EmployBoard> findAllByDeletedAtIsNull(Pageable pageable);

    // 상세 조회
    Optional<EmployBoard> findByIdAndDeletedAtIsNull(Long id);
}
