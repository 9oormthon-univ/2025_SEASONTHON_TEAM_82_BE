package com.bridgeon.app.domain.attachment.repository;

import com.bridgeon.app.domain.attachment.entity.Attachment;
import com.bridgeon.app.global.enums.attachment.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentJpaRepository extends JpaRepository<Attachment, Long> {

    // 대상별 첨부파일 목록 (정렬 순서 오름차순) → 자유/구인/포트폴리오/사업계획서
    List<Attachment> findByTargetTypeAndTargetIdOrderBySortOrderAsc(TargetType targetType, Long targetId);

    // 대표 이미지 단건 조회 (sortOrder 가장 작은 파일) → 프로필
    Optional<Attachment> findFirstByTargetTypeAndTargetIdOrderBySortOrderAsc(TargetType targetType, Long targetId);

    // 대상별 전체 삭제 (치환/재업로드 시 사용)
    void deleteByTargetTypeAndTargetId(TargetType targetType, Long targetId);

    // 대상 존재 여부 확인
    boolean existsByTargetTypeAndTargetId(TargetType targetType, Long targetId);
}
