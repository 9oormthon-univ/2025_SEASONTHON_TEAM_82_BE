package com.bridgeon.app.domain.board.service;

import com.bridgeon.app.domain.board.repository.ReadAuthorizationJpaRepository;
import com.bridgeon.app.domain.board.usecase.CheckReadPermissionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReadAuthorizationService implements CheckReadPermissionUseCase {

    private final ReadAuthorizationJpaRepository readAuthorizationJpaRepository;

    @Override
    public boolean canReadBusinessPlan(Long businessPlanId, Long currentUserId) {
        return readAuthorizationJpaRepository
                .existsByBusinessPlan_IdAndUser_IdAndIsAcceptedTrue(businessPlanId, currentUserId);
    }
}
