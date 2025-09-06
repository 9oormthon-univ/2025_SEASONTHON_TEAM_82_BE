package com.bridgeon.app.global.exception.error;

import com.bridgeon.app.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BusinessPlanErrorCode implements ErrorCode {

    // 400
    INVALID_TITLE(HttpStatus.BAD_REQUEST.value(), "B4001", "제목이 유효하지 않습니다."),
    INVALID_CONTENT(HttpStatus.BAD_REQUEST.value(), "B4002", "사업계획서 내용이 유효하지 않습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST.value(), "B4003", "파일 업로드에 실패했습니다."),

    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "B4011", "인증되지 않은 사용자입니다."),

    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "B4031", "해당 사업계획서를 열람할 권한이 없습니다."),

    // 404
    BUSINESS_PLAN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "B4041", "사업계획서를 찾을 수 없습니다."),

    // 500
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "B5001", "사업계획서 처리 중 서버 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}

