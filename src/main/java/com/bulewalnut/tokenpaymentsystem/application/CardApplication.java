package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.api.CardApi;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardApplication {

    private final CardApi cardApi;

    public String encryptAndRegisterCard(CardDto cardDto) {
        return cardApi.encryptAndRegisterCard(cardDto);
    }

    public List<CardDto> findCardByUserCi() {
        return cardApi.findCardByUserCi("test123");
    }

    public PaymentRecordDto paymentProcessByToken(PaymentDto paymentDto) {
        String token = cardApi.getTokenByRefId(paymentDto.getRefId()); // 1회용 토큰 발급
        PaymentDto requestDto = PaymentDto.of(paymentDto, token);

        return cardApi.paymentProcessByToken(requestDto);
    }
}


