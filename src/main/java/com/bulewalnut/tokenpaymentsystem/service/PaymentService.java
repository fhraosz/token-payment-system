package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.api.PaymentApi;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto;
import com.bulewalnut.tokenpaymentsystem.entity.PaymentRecordEntity;
import com.bulewalnut.tokenpaymentsystem.exception.PaymentException;
import com.bulewalnut.tokenpaymentsystem.repository.PaymentRecordRepository;
import com.bulewalnut.tokenpaymentsystem.type.MessageTypeEnum;
import com.bulewalnut.tokenpaymentsystem.type.PaymentStateEnum;
import com.bulewalnut.tokenpaymentsystem.util.DateUtil;
import com.bulewalnut.tokenpaymentsystem.util.RandomKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

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

        LocalDateTime now = LocalDateTime.now();
        String paymentCompleteDate = DateUtil.formatToDate(now);

        try {
            boolean isSuccess = true;

            if (isSuccess) {
                paymentRecordEntity = savePaymentRecordEntity(requestDto, transactionId, PaymentStateEnum.APPROVED.getState(), now, paymentCompleteDate);
            } else {
                paymentRecordEntity = savePaymentRecordEntity(requestDto, transactionId, PaymentStateEnum.FAILED.getState(), now, null);
            }

            Boolean isChangeToken = paymentApi.changeStateToken(TokenRequestDto.setTokenRequestDto(requestDto.getToken()));

            if (BooleanUtils.isNotTrue(isChangeToken)) {
                log.error(MessageTypeEnum.CHANGE_TOKEN_STATE_FAIL.getMessage());
            } else {
                log.info(MessageTypeEnum.CHANGE_TOKEN_STATE_SUCCESS.getMessage());
            }

            return PaymentRecordDto.of(paymentRecordEntity);

        } catch (PaymentException e) {
            log.error(MessageTypeEnum.PAYMENT_PROCESS_FAIL.getMessage(), e);
            throw new PaymentException(MessageTypeEnum.PAYMENT_PROCESS_FAIL.getMessage(), e);
        }
    }

    @Transactional
    protected PaymentRecordEntity savePaymentRecordEntity(PaymentDto requestDto, String transactionId, String state, LocalDateTime now, String paymentCompleteDate) {
        return paymentRecordRepository.save(PaymentRecordEntity.bulidPaymentRecordEntity(requestDto, transactionId, state, now, paymentCompleteDate));
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
