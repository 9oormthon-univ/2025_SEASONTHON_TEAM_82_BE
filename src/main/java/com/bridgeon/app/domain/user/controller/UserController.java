package com.bridgeon.app.domain.user.controller;

import com.bridgeon.app.domain.user.dto.response.MyPageResponseDto;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.usecase.UserUseCase;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/me")
    public ResponseDto<MyPageResponseDto> getUserInfo(
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        MyPageResponseDto data = userUseCase.getUserInfo(user.getId());

        return ResponseDto.success(
                HttpStatus.OK,
                "마이페이지 조회 성공",
                data
        );
    }
}
