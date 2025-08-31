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

    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(),"A4011", "인증되지 않은 사용자압니다."),

    // 403
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "A4031", "접근 권한이 없습니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "A4041", "사용자를 찾을 수 없습니다."),;

    private final int httpStatus;
    private final String code;
    private final String message;
}
