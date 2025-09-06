package com.bridgeon.app.domain.board.usecase;

public interface ReadAuthorizationUseCase {
    Long apply(Long employBoardId, Long requesterId);
    void approve(Long employBoardId, Long readAuthId, Long ownerId);
    void reject(Long employBoardId, Long readAuthId, Long ownerId);
    void cancel(Long employBoardId, Long readAuthId, Long requesterId);
}
