package com.bulewalnut.tokenpaymentsystem.controller;

import com.bulewalnut.tokenpaymentsystem.application.PaymentApplication;
import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto;
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

    /**
     * 결제 프로세스
     */
    @PostMapping("/approve")
    public ResponseEntity<ResponseDto<PaymentRecordDto>> paymentProcessByPayment(@RequestBody PaymentDto requestDto) {
        return HttpBuilder.createResponse(paymentApplication.validateTokenAndProcessPayment(requestDto));
    }

    /**
     * 결제 내역 가져오기
     */
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<PaymentRecordDto>> findPaymentRecordByTransactionId(@RequestBody String transactionId) {
        return HttpBuilder.createResponse(paymentApplication.findPaymentRecordByTransactionId(transactionId));
    }

}
