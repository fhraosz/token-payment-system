package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ResponseDto<T> setResponseDto(boolean success, String message, T data) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
