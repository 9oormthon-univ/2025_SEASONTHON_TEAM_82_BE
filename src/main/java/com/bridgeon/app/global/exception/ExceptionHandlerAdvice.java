package com.bridgeon.app.global.exception;

import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getHttpStatus()));
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getHttpStatus()));
    }

    // 📝 business logic error exception
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException e
    ) {
        log.warn("handleInternalLogicException(BusinessException)", e);
        final ErrorCode code = e.getErrorCode();
        return createErrorResponse(code);
    }


    // binding error exception caused by @Valid or @Validated
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        log.warn("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(
                CommonErrorCode.INVALID_INPUT_VALUE,
                e.getBindingResult()
        );
        return createErrorResponse(response);
    }


    // error exception when binding fails due to mismatch in enum type
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e
    ) {
        log.warn("handleMethodArgumentTypeMismatchException", e);
        final ErrorResponse response = ErrorResponse.of(e);
        return createErrorResponse(response);
    }


    // error exception when using an unsupported HTTP method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e
    ) {
        log.warn("handleHttpRequestMethodNotSupportedException", e);
        return createErrorResponse(CommonErrorCode.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        log.warn("handleIllegalArgumentException", e);
        return createErrorResponse(CommonErrorCode.INVALID_INPUT_VALUE);
    }


    // rest error exception ...
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e
    ) {
        log.error("handleException - Unexpected error occurred", e);
        return createErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
}
