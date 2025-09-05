package com.bridgeon.app.domain.info.repository;

import com.bridgeon.app.domain.info.entity.FoundationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundationInfoJpaRepository extends JpaRepository<FoundationInfo, Long> {
    //조회수 기준 내림차순
    Page<FoundationInfo> findAllByOrderByViewDesc(Pageable pageable);
}
