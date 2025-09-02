package com.bridgeon.app.global.exception.error;


import com.bridgeon.app.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FreeErrorCode implements ErrorCode {

    // 400
    TITLE_REQUIRED(HttpStatus.BAD_REQUEST.value(), "F4001", "제목은 반드시 입력해야 합니다."),
    CONTENT_REQUIRED(HttpStatus.BAD_REQUEST.value(), "F4002", "내용은 반드시 입력해야 합니다."),

    // 404
    FREE_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "F4041", "자유게시판 글을 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
