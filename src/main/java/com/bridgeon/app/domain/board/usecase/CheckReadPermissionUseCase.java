package com.bridgeon.app.domain.board.usecase;

public interface CheckReadPermissionUseCase {
    boolean canReadBusinessPlan(Long businessPlanId, Long currentUserId);;
}
