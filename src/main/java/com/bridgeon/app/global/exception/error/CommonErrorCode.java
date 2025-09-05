package com.bridgeon.app.global.exception.error;

import com.bridgeon.app.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonErrorCode implements ErrorCode {

    // 400
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), "C4001", "유효하지 않은 타입입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "C4002", "입력된 값이 유효하지 않습니다."),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "C4003", "엔티티를 찾을 수 없습니다."),
    NO_REQUIRED_FILES(HttpStatus.BAD_REQUEST.value(), "C4005", "필수 제출 파일이 누락되었습니다."),

    // 404
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C404", "리소스를 찾을 수 없습니다."),

    // 405
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "C4054", "유효하지 않은 HTTP Method입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C5001", "Internal Server Error");


    private final int httpStatus;
    private final String code;
    private final String message;
}
