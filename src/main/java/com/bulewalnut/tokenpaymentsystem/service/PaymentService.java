package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.api.PaymentApi;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto;
import com.bulewalnut.tokenpaymentsystem.entity.PaymentRecordEntity;
import com.bulewalnut.tokenpaymentsystem.repository.PaymentRecordRepository;
import com.bulewalnut.tokenpaymentsystem.type.PaymentStateEnum;
import com.bulewalnut.tokenpaymentsystem.util.RandomKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentApi paymentApi;

    public PaymentRecordDto processPayment(PaymentDto requestDto) {

        boolean isSuccess = true;
        String transactionId = RandomKeyUtil.createTransactionId();
        PaymentRecordEntity paymentRecordEntity;

        if (isSuccess) {
            paymentRecordEntity = paymentRecordRepository.save(PaymentRecordEntity.of(requestDto, transactionId, PaymentStateEnum.APPROVED.getState()));
        } else {
            paymentRecordEntity = paymentRecordRepository.save(PaymentRecordEntity.of(requestDto, transactionId, PaymentStateEnum.FAILED.getState()));
        }

        // 토큰상태변경
        try {
            Boolean isChangeToken = paymentApi.changeStateToken(TokenRequestDto.of(requestDto.getToken()));
            if (isChangeToken) {
                log.info("토큰상태변경 완료");
            } else {
                log.info("토큰상태변경 실패");
            }
        } catch (Exception e) {
            log.info("토큰상태변경 실패", e);
        }

        return PaymentRecordDto.of(paymentRecordEntity);
    }
}
