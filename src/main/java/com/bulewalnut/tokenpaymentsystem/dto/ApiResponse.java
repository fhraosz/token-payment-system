package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private String message;
    private T data;

    public static <T> ApiResponse<T> of(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> of(String message) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(null)
                .build();
    }
}
