package com.bridgeon.app.domain.user.repository;

import com.bridgeon.app.domain.user.entity.Portfolio;
import com.bridgeon.app.global.enums.portfolio.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioJpaRepository extends JpaRepository<Portfolio, Long> {
        List<Portfolio> findByUser_IdOrderByCreatedAtDesc(Long userId);
        List<Portfolio> findByUser_IdAndVisibilityOrderByCreatedAtDesc(Long userId, Visibility visibility);

        List<Portfolio> findAllByUser_IdOrderByStartDateDesc(Long userId);
}
