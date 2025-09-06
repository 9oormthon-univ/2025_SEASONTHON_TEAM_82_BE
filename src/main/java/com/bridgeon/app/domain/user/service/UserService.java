package com.bridgeon.app.domain.user.service;

import com.bridgeon.app.domain.user.dto.response.MyPageResponseDto;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.UserRepository;
import com.bridgeon.app.domain.user.usecase.UserUseCase;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    public MyPageResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));

        return new MyPageResponseDto(
                user.getId(),
                user.getProvider(),
                user.getRole(),
                user.getEmail(),
                user.getName(),
                user.getNickname(),
                user.getRegion(),
                user.getInterestField(),
                user.getIntroduction()
        );
    }
}
