package com.bridgeon.app.domain.user.service;

import com.bridgeon.app.domain.user.dto.request.SignUpRequestDto;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.domain.user.repository.UserRepository;
import com.bridgeon.app.domain.user.usecase.AuthUseCase;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepository userRepository;

    @Transactional
    public User signUp(SignUpRequestDto request) {
        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new BusinessException(AuthErrorCode.DUPLICATE_USER);
                });

        User user = User.builder()
                .provider(request.provider())
                .role(request.role())
                .email(request.email())
                .name(request.name())
                .nickname(request.name()) // 일단 name 값을 넣어두고 추후 수정 가능하도록 함
                .region(null)
                .interestField(null)
                .introduction(null)
                .deletedAt(null)
                .build();

        return userRepository.save(user);
    }
}
