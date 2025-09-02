package com.bridgeon.app.global.exception.error;


import com.bridgeon.app.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InfoErrorCode implements ErrorCode {

    // 404
    FOUNDATION_INFO_NOT_FOUND(404, "FND_4001", "조회 가능한 창업 정보가 없습니다."),

    // 400
    INVALID_PAGE_REQUEST(400, "FND_4002", "잘못된 페이지 요청입니다."),
    INVALID_DATE_RANGE(400, "FND_4003", "잘못된 날짜 범위입니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}