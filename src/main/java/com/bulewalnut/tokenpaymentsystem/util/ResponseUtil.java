package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    /**
     * AJAX 요청에 대한 응답을 생성하는 유틸리티 메서드
     */
    public static <T> ResponseEntity<ResponseDto<T>> createResponse(ResponseDto<T> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
