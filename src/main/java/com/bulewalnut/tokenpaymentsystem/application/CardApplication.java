package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.api.CardApi;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto;
import com.bulewalnut.tokenpaymentsystem.exception.EncryptionException;
import com.bulewalnut.tokenpaymentsystem.type.MessageTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardApplication {

    public static final String ERROR = "error";
    public static final String PAYMENT_RESPONSE = "paymentResponse";

    private final CardApi cardApi;

    @Value("${test.user_ci}")
    private String userCi;

    /**
     * 카드 정보를 암호화하고 등록
     */
    public ResponseDto<String> encryptAndRegisterCard(CardDto cardDto) {
        try{
            String refId = cardApi.encryptAndRegisterCard(cardDto);
            if (refId == null) {
                log.error(MessageTypeEnum.MAKE_REF_ID_FAIL.getMessage());
                return ResponseDto.setResponseDto(false, MessageTypeEnum.CARD_REGISTER_FAIL.getMessage(), null);
            }
            return ResponseDto.setResponseDto(true, String.format("%s REF_ID: %s", MessageTypeEnum.CARD_REGISTER_SUCCESS.getMessage(), refId), refId);
        } catch (EncryptionException e) {
            log.error(MessageTypeEnum.CARD_REGISTER_FAIL.getMessage());
            return ResponseDto.setResponseDto(false, MessageTypeEnum.CARD_REGISTER_FAIL.getMessage(), null);
        }
    }

    /**
     * 사용자 CI 정보를 기준으로 등록된 카드를 조회
     */
    public List<CardDto> findCardByUserCi() {
        return cardApi.findCardByUserCi(userCi);
    }

    /**
     * 주어진 결제 정보를 기반으로 토큰을 발급받아 결제 처리
     */
    public ResponseDto<PaymentRecordDto> paymentProcessByToken(PaymentDto paymentDto) {
        // RefId 기반으로 토큰을 발급받는다
        String token = cardApi.getTokenByRefId(paymentDto.getRefId());

        if (token == null) {
            return ResponseDto.setResponseDto(false, MessageTypeEnum.PAYMENT_PROCESS_FAIL.getMessage(), null);
        }
        PaymentDto requestDto = PaymentDto.setPaymentDto(paymentDto, token);
        // 결제를 시도한다
        PaymentRecordDto paymentRecordDto = cardApi.paymentProcessByToken(requestDto);

        if (paymentRecordDto == null) {
            return ResponseDto.setResponseDto(false, MessageTypeEnum.PAYMENT_PROCESS_FAIL.getMessage(), new PaymentRecordDto());
        }

        return ResponseDto.setResponseDto(true, MessageTypeEnum.PAYMENT_PROCESS_SUCCESS.getMessage(), paymentRecordDto);
    }

    /**
     *  transactionId 정보를 기준으로 결제내역 조회
     */
    public PaymentRecordDto findPaymentRecordByTransactionId(String transactionId) {
        return cardApi.findPaymentRecordByTransactionId(transactionId);
    }
}
