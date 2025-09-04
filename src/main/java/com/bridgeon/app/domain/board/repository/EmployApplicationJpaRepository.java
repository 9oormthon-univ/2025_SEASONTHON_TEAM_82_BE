package com.bridgeon.app.domain.board.repository;

import com.bridgeon.app.domain.board.entity.EmployApplication;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmployApplicationJpaRepository extends JpaRepository<EmployApplication, Long> {

    // 중복 지원 방지 체크
    boolean existsByEmployBoard_IdAndApplicant_Id(Long employBoardId, Long userId);

    // 현재 글에 내가 지원했는지/상태 확인
    Optional<EmployApplication> findByEmployBoard_IdAndApplicant_Id(Long employBoardId, Long userId);

    // 작성자용 받은 지원 목록
    Page<EmployApplication> findByEmployBoard_Id(Long employBoardId, Pageable pageable);
    Page<EmployApplication> findByEmployBoard_IdAndApplyStatus(Long employBoardId, ApplyStatus status, Pageable pageable);

    // 지원자용 내 지원 목록
    Page<EmployApplication> findByApplicant_Id(Long userId, Pageable pageable);
    Page<EmployApplication> findByApplicant_IdAndApplyStatus(Long userId, ApplyStatus status, Pageable pageable);

    // 상태 전이(승인/거절)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update EmployApplication a
              set a.applyStatus = :status,
                  a.decidedAt   = :decidedAt
            where a.id          = :applicationId
           """)
    int updateStatus(@Param("applicationId") Long applicationId,
                     @Param("status") ApplyStatus status,
                     @Param("decidedAt") LocalDateTime decidedAt);

    // === 소프트 삭제(지원 취소) ===
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update EmployApplication a
              set a.deletedAt = :deletedAt
            where a.id        = :applicationId
           """)
    int softDelete(@Param("applicationId") Long applicationId,
                   @Param("deletedAt") LocalDateTime deletedAt);
}
