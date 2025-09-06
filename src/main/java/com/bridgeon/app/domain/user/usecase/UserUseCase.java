package com.bridgeon.app.domain.user.usecase;

import com.bridgeon.app.domain.user.dto.response.MyPageResponseDto;

public interface UserUseCase {

    MyPageResponseDto getUserInfo(Long userId);
}
