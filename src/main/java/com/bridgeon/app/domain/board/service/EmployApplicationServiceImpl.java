package com.bridgeon.app.domain.board.service;

import com.bridgeon.app.domain.board.entity.EmployApplication;
import com.bridgeon.app.domain.board.entity.EmployBoard;
import com.bridgeon.app.domain.board.repository.EmployApplicationJpaRepository;
import com.bridgeon.app.domain.board.repository.EmployBoardJpaRepository;
import com.bridgeon.app.domain.board.usecase.EmployApplicationUseCase;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.UserRepository;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.bridgeon.app.global.exception.error.EmployErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployApplicationServiceImpl implements EmployApplicationUseCase {

    private final EmployApplicationJpaRepository applicationRepository;
    private final EmployBoardJpaRepository boardRepository;
    private final UserRepository userRepository;


    // 지원 신청
    @Override
    @Transactional
    public Long apply(Long employBoardId, Long applicantId) {

        //모집글 검증 (404)
        EmployBoard board = boardRepository.findById(employBoardId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.EMPLOY_BOARD_NOT_FOUND));


        // 자신의 글에는 지원 금지
        if (board.getUser().getId().equals(applicantId)) {
            throw new BusinessException(EmployErrorCode.CANNOT_APPLY_OWN_POST);
        }

        //중복 지원 방지
        if (applicationRepository.existsByEmployBoard_IdAndApplicant_Id(employBoardId, applicantId)) {
            throw new BusinessException(EmployErrorCode.DUPLICATED_APPLICATION);
        }

        // 지원자 유저 검증
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));


        //신청 엔티티 생성
        EmployApplication saved = EmployApplication.builder()
                .employBoard(board) // 어떤 모집글에 대한 지원
                .applicant(applicant) // 지원자 정보
                .applyStatus(ApplyStatus.PENDING) // 초기 상태는 대기중
                .build();
        //DB에 저장
        saved = applicationRepository.save(saved);

        return saved.getId();
    }

    // 승인하기
    @Override
    @Transactional
    public void approve(Long employBoardId, Long applicationId, Long ownerId) {

        // 신청 존재 확인
        EmployApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.APPLICATION_NOT_FOUND));

        // 글 작성자인지 확인
        validateOwnership(app, employBoardId, ownerId);

        // 승인으로 업데이트
        int updated = applicationRepository.updateStatus(
                applicationId,
                ApplyStatus.APPROVED,
                LocalDateTime.now()
        );
        // 업데이트 된 행이 없을 때 -> 예외 던짐
        if (updated == 0) throw new BusinessException(EmployErrorCode.APPLICATION_NOT_FOUND);
    }

    // 거절하기
    @Override
    @Transactional
    public void reject(Long employBoardId, Long applicationId, Long ownerId) {

        // 신청 존재 확인
        EmployApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.APPLICATION_NOT_FOUND));

        // 글 작성자인지 확인
        validateOwnership(app, employBoardId, ownerId);

        // 거절으로 업데이트
        int updated = applicationRepository.updateStatus(
                applicationId,
                ApplyStatus.REJECTED,
                LocalDateTime.now()
        );
        //업데이트 된 행이 없을 때 -> 예외 던짐
        if (updated == 0) throw new BusinessException(EmployErrorCode.APPLICATION_NOT_FOUND);
    }

    // 지원 취소
    @Override
    @Transactional
    public void cancel(Long employBoardId, Long applicationId, Long ownerId) {
        // 신청 존재 확인
        EmployApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.APPLICATION_NOT_FOUND));

        // 경로 검증
        if (!app.getEmployBoard().getId().equals(employBoardId)) {
            throw new BusinessException(EmployErrorCode.APPLICATION_NOT_FOUND);
        }

        //신청자 본인만 취소 가능
        if (!app.getApplicant().getId().equals(applicationId)) {
            throw new BusinessException(AuthErrorCode.ACCESS_DENIED);
        }

        //softdelete 구현
        applicationRepository.softDelete(applicationId, LocalDateTime.now());
    }


    private void validateOwnership(EmployApplication app, Long employBoardId, Long ownerId) {
        // 신청이 해당 게시글에 속해 있는지 확인
        if (!app.getEmployBoard().getId().equals(employBoardId)) {
            throw new BusinessException(EmployErrorCode.EMPLOY_APPLICATION_NOT_FOUND);
        }

        // 게시글 작성자(ownerId)와 요청한 사용자(ownerId)가 일치하는지 확인
        if (!app.getEmployBoard().getUser().getId().equals(ownerId)) {
            throw new BusinessException(AuthErrorCode.ACCESS_DENIED);
        }
    }
}