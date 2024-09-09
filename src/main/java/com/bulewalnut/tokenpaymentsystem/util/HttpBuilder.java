package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> createResponse(String message, T data, HttpStatus httpStatus) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    public static <T> ResponseEntity<ApiResponse<T>> createResponse(T data) {
        return createResponse("success", data, HttpStatus.OK);
    }
}
