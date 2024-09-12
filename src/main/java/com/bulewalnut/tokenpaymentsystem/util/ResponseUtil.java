package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    /**
     * AJAX 요청에 대한 응답을 생성하는 유틸리티 메서드
     */
    public static <T> ResponseEntity<ResponseDto<T>> createResponse(ResponseDto<T> result) {
        if (result == null || BooleanUtils.isNotTrue(result.getSuccess())) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
