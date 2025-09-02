package com.bridgeon.app.domain.info.repository;

import com.bridgeon.app.domain.info.entity.FoundationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundationInfoJpaRepository extends JpaRepository<FoundationInfo, Long> {
}
