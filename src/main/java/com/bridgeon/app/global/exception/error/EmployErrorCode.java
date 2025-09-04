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

    // 400 잘못된 요청
    INVALID_SEARCH_CONDITION(HttpStatus.BAD_REQUEST.value(), "E4001", "잘못된 검색 조건입니다."),
    TITLE_REQUIRED(HttpStatus.BAD_REQUEST.value(), "E4002", "제목은 반드시 입력해야 합니다."),
    PAGE_SIZE_TOO_LARGE(HttpStatus.BAD_REQUEST.value(), "E4003", "요청한 페이지 크기가 너무 큽니다."),
    CANNOT_APPLY_OWN_POST(HttpStatus.BAD_REQUEST.value(), "E4004", "자신이 작성한 글에는 지원할 수 없습니다."),
    DUPLICATED_APPLICATION(HttpStatus.BAD_REQUEST.value(), "E4005", "이미 해당 글에 지원한 이력이 있습니다."),

    // 403 권한 오류
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "E4031", "권한이 없습니다."),

    // 404 리소스 없음
    EMPLOY_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E4041", "구인 게시글을 찾을 수 없습니다."),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E4042", "지원 내역을 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}