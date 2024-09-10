package com.bulewalnut.tokenpaymentsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> {
    private Boolean success; // 성공여부
    private String message; // 메시지
    private T data; // 데이터

    public static <T> ResponseDto<T> setResponseDto(Boolean success, String message, T data) {
        ResponseDto<T> response = new ResponseDto<>();

        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);

        return response;
    }
}
