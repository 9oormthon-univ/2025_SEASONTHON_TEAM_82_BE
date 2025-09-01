package com.bridgeon.app.global.exception.error;

import com.bridgeon.app.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmployErrorCode implements ErrorCode {

    // 400
    INVALID_SEARCH_CONDITION(HttpStatus.BAD_REQUEST.value(), "E4001", "잘못된 검색 조건입니다."),
    TITLE_REQUIRED(HttpStatus.BAD_REQUEST.value(), "E4002", "제목은 반드시 입력해야 합니다."),
    PAGE_SIZE_TOO_LARGE(HttpStatus.BAD_REQUEST.value(), "E4003", "요청한 페이지 크기가 너무 큽니다."),

    // 404
    EMPLOY_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E4041", "구인 게시글을 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}