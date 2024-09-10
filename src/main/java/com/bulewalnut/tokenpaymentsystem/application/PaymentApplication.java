package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.api.PaymentApi;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto;
import com.bulewalnut.tokenpaymentsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentApplication {

    private final PaymentApi paymentApi;
    private final PaymentService paymentService;

    public PaymentRecordDto validateTokenAndProcessPayment(PaymentDto requestDto) {
        TokenRequestDto tokenRequestDto = TokenRequestDto.setTokenRequestDto(requestDto.getToken());

        // 토큰 유효성 검사
        if (BooleanUtils.isNotTrue(paymentApi.validateToken(tokenRequestDto))) {
            return new PaymentRecordDto();
        }
        return paymentService.processPayment(requestDto);
    }

    public PaymentRecordDto findPaymentRecordByTransactionId(String transactionId) {
        return paymentService.findPaymentRecordByTransactionId(transactionId);
    }
}


