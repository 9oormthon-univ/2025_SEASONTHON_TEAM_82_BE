package com.bridgeon.app.domain.board.repositroy;

import com.bridgeon.app.domain.board.entity.EmployBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployBoardJpaRepository extends JpaRepository<EmployBoard, Long> {

}
