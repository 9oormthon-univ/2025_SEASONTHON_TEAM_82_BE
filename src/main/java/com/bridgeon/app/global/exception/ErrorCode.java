package com.bridgeon.app.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    int getHttpStatus();
    String getCode();
    String getMessage();

    default HttpStatus getHttpStatusCode() {
        return HttpStatus.valueOf(getHttpStatus());
    }

    default void validate() {
        if (getHttpStatus() < 100 || getHttpStatus() > 599) {
            throw new IllegalArgumentException("유효하지 않은 HTTP status 입니다.: " + getHttpStatus());
        }
        if (getCode() == null || getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("에러 코드는 비어있을 수 없습니다.");
        }
    }
}
