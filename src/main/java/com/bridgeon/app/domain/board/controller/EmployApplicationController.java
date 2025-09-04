package com.bridgeon.app.domain.board.controller;


import com.bridgeon.app.domain.board.usecase.EmployApplicationUseCase;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/employ/posts")
@RequiredArgsConstructor
public class EmployApplicationController {

    private final EmployApplicationUseCase employApplicationUseCase;

    // 지원하기
    @PostMapping("/{employBoardId}/applications")
    public ResponseDto<Long> apply(
            @AuthenticationPrincipal User user,
            @PathVariable Long employBoardId
    ) {
        Long applicationId = employApplicationUseCase.apply(employBoardId, user.getId());

        return new ResponseDto<>(
                HttpStatus.CREATED.value(),
                "지원이 접수되었습니다.",
                applicationId
        );
    }

    // 지원 승인 (작성자)
    @PostMapping("/{employBoardId}/applications/{applicationId}/approve")
    public ResponseDto<Void> approve(
            @AuthenticationPrincipal User user,
            @PathVariable Long employBoardId,
            @PathVariable Long applicationId
    ) {
        employApplicationUseCase.approve(employBoardId, applicationId, user.getId());
        return new ResponseDto<>(HttpStatus.OK.value(), "승인 완료", null);
    }

    // 지원 거절 (작성자)
    @PostMapping("/{employBoardId}/applications/{applicationId}/reject")
    public ResponseDto<Void> reject(
            @AuthenticationPrincipal User user,
            @PathVariable Long employBoardId,
            @PathVariable Long applicationId
    ) {
        employApplicationUseCase.reject(employBoardId, applicationId, user.getId());
        return new ResponseDto<>(HttpStatus.OK.value(), "거절 완료", null);
    }

    // 지원 취소 (지원자)
    @DeleteMapping("/{employBoardId}/applications/{applicationId}")
    public ResponseDto<Void> cancel(
            @AuthenticationPrincipal User user,
            @PathVariable Long employBoardId,
            @PathVariable Long applicationId
    ) {
        employApplicationUseCase.cancel(employBoardId, applicationId, user.getId());
        return new ResponseDto<>(HttpStatus.OK.value(), "취소 완료", null);
    }
}