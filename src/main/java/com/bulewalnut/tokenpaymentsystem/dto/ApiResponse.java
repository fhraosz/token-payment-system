package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private String message;
    private Boolean success;
    private T data;
}
