package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.PaymentApplication;
import com.bulewalnut.tokenpaymentsystem.dto.ApiResponse;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import com.bulewalnut.tokenpaymentsystem.util.HttpBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentApplication paymentApplication;

    // 카드결제
    @PostMapping("/approve")
    public ResponseEntity<ApiResponse<PaymentRecordDto>> paymentProcessByPayment(@RequestBody PaymentDto requestDto) {
        return HttpBuilder.createResponse(paymentApplication.validateTokenAndProcessPayment(requestDto));
    }

}
