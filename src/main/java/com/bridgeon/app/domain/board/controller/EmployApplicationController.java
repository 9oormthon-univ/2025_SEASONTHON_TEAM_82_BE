package com.bridgeon.app.domain.board.controller;


import com.bridgeon.app.domain.board.usecase.EmployApplicationUseCase;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.bridgeon.app.global.jwt.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employ/posts")
@RequiredArgsConstructor
public class EmployApplicationController {

    private final EmployApplicationUseCase employApplicationUseCase;
    private final JwtUtils jwtUtils;

    // 지원하기
    @PostMapping("/{employBoardId}/applications")
    public ResponseDto<Long> apply(
            @AuthenticationPrincipal Object principal,  // <- SpEL 제거, Object로 받기
            @PathVariable Long employBoardId
    ) {
        // 인증 체크: 토큰 만료/미포함이면 Anonymous로 들어와서 String 입니다.
        if (!(principal instanceof org.springframework.security.core.userdetails.User u)) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }


        // JwtUtils.getAuthentication()에서 subject를 username으로 넣었으므로 그대로 사용
        String userIdStr = u.getUsername();

        Long applicantId;
        try {
            applicantId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        Long applicationId = employApplicationUseCase.apply(employBoardId, applicantId);

        return new ResponseDto<>(
                HttpStatus.CREATED.value(),
                "지원이 접수되었습니다.",
                applicationId
        );
    }
}