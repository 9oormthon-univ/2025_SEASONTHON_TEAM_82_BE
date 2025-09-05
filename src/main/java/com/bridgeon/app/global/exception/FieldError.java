package com.bridgeon.app.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldError {

    private String field;
    private String value;
    private String reason;

    public static List<FieldError> of(
            final String field,
            final String value,
            final String reason
    ) {
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError(
                field != null ? field : "unknown",
                value != null ? value : "null",
                reason != null ? reason : "validation failed"
        ));
        return fieldErrors;
    }

    public static List<FieldError> of(
            final BindingResult bindingResult
    ) {
        if (bindingResult == null || !bindingResult.hasFieldErrors()) {
            return new ArrayList<>();
        }

        final List<org.springframework.validation.FieldError>  fieldErrors = bindingResult.getFieldErrors();

        return fieldErrors
                .stream()
                .map(error -> new FieldError(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage() != null ? error.getDefaultMessage() : "validation failed"
                ))
                .collect(Collectors.toList());
    }
}
