package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.api.PaymentApi;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto;
import com.bulewalnut.tokenpaymentsystem.entity.PaymentRecordEntity;
import com.bulewalnut.tokenpaymentsystem.exception.PaymentException;
import com.bulewalnut.tokenpaymentsystem.repository.PaymentRecordRepository;
import com.bulewalnut.tokenpaymentsystem.type.PaymentStateEnum;
import com.bulewalnut.tokenpaymentsystem.util.RandomKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentApi paymentApi;

    @Transactional
    public PaymentRecordDto processPayment(PaymentDto requestDto) {
        String transactionId = RandomKeyUtil.createTransactionId();
        PaymentRecordEntity paymentRecordEntity;

        try {
            boolean isSuccess = true;
            if (isSuccess) { // 다른값만 파라미터로 넘겨서 save 한줄로 만들기
                paymentRecordEntity = paymentRecordRepository.save(PaymentRecordEntity.of(requestDto, transactionId, PaymentStateEnum.APPROVED.getState()));
            } else {
                paymentRecordEntity = paymentRecordRepository.save(PaymentRecordEntity.of(requestDto, transactionId, PaymentStateEnum.FAILED.getState()));
            }

            Boolean isChangeToken = paymentApi.changeStateToken(TokenRequestDto.setTokenRequestDto(requestDto.getToken()));
            if (!isChangeToken) {
                log.info("토큰 상태 변경 실패");
                // 토큰상태 변경에 실패해도 결제가 성공하면 패스 후 로그만 남긴다.
            } else {
                log.info("결제 및 토큰 상태 변경 완료");
            } // 결과 값 그냥 로그에 찍어주기 if 필요 x
            // entity null체크 후 생성
            return PaymentRecordDto.of(paymentRecordEntity);

        } catch (PaymentException e) {
            log.error("결제 처리 중 오류 발생: {}", e.getMessage(), e);
            throw new PaymentException("결제 처리에 실패하였습니다.", e);
        }
    }

    @Transactional
    public PaymentRecordDto findPaymentRecordByTransactionId(String transactionId) {
        PaymentRecordEntity entity = paymentRecordRepository.findByTransactionId(transactionId);

        if (entity == null) {
           return new PaymentRecordDto();
        }

        return PaymentRecordDto.of(entity);
    }
}
