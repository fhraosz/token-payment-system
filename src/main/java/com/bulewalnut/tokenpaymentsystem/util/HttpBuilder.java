package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpBuilder {

    public static final String SUCCESS = "success";

    public static <T> ResponseEntity<ResponseDto<T>> createResponse(boolean success, String message, T data, HttpStatus httpStatus) {
        ResponseDto<T> response = ResponseDto.setResponseDto(success, message, data);
        return new ResponseEntity<>(response, httpStatus);
    }

    public static <T> ResponseEntity<ResponseDto<T>> createResponse(T data) {
        return createResponse(true, SUCCESS, data, HttpStatus.OK);
    }
}
