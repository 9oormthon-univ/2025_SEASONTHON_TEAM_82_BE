package com.bridgeon.app.global.exception.error;

import com.bridgeon.app.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AuthErrorCode implements ErrorCode {

    // 400
    UNSUPPORTED_SOCIAL_LOGIN(HttpStatus.BAD_REQUEST.value(), "A4001", "지원하지 않는 소셜 로그인입니다."),
    INVALID_KAKAO_RESPONSE(HttpStatus.BAD_REQUEST.value(), "A4002", "카카오 응답 형식이 유효하지 않습니다."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST.value(), "A4003", "이미 가입된 사용자입니다."),
    PERMISSION_CONSENT(HttpStatus.BAD_REQUEST.value(), "A4004", "카카오 계정 권한 동의 필요"),

    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(),"A4011", "인증되지 않은 사용자입니다."),

    // 403
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "A4031", "접근 권한이 없습니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "A4041", "사용자를 찾을 수 없습니다."),

    // 503
    DENIED_KAKAO_TOKEN(HttpStatus.SERVICE_UNAVAILABLE.value(), "A5031", "카카오 토큰 발급 실패"),
    DENIED_KAKAO_USER(HttpStatus.SERVICE_UNAVAILABLE.value(), "A5032", "카카오 사용자 조회 실패");

    private final int httpStatus;
    private final String code;
    private final String message;
}
