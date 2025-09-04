package com.bridgeon.app.domain.board.usecase;


public interface EmployApplicationUseCase {

    // 지원하기
    Long apply(Long employBoardId, Long applicantId);

    // 구인게시판 글 작성자가 승인
    void approve(Long employBoardId, Long applicationId, Long ownerId);

    // 구인게시판 글 작성자가 거절
    void reject(Long employBoardId, Long applicationId, Long ownerId);

    // 지원자가 취소
    void cancel(Long employBoardId, Long applicationId, Long applicantId);
}
