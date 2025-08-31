package com.bridgeon.app.global.exception;

import com.bridgeon.app.global.exception.error.CommonErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private int httpStatus;
    private String code;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private List<FieldError> fieldErrors;

    private ErrorResponse(final ErrorCode errorCode) {
        errorCode.validate();
        this.httpStatus = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.fieldErrors = new ArrayList<>();
    }

    private ErrorResponse(final ErrorCode errorCode, final List<FieldError> fieldErrors) {
        errorCode.validate();
        this.httpStatus = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.fieldErrors = fieldErrors != null ? new ArrayList<>(fieldErrors) : new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(final ErrorCode errorCode, final List<FieldError> fieldErrors) {
        return new ErrorResponse(errorCode, fieldErrors);
    }

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> fieldErrors = new ArrayList<>();
        return new ErrorResponse(CommonErrorCode.INVALID_INPUT_VALUE, fieldErrors);
    }
}