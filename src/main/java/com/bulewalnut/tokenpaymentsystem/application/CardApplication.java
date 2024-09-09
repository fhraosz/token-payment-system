package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardApplication {

    private final CardService cardService;

    public String encryptAndRegisterCard(CardDto cardDto) {
        return cardService.encryptAndRegisterCard(cardDto);
    }

    public List<CardDto> findCardByUserCi() {
        return cardService.findCardByUserCi("test123");
    }

    public String paymentProcessByPaymentDto(PaymentDto paymentDto) {
        String oneTimeToken = cardService.getTokenByRefId(paymentDto.getRefId());
        return cardService.paymentProcessByPaymentDto(PaymentDto.of(paymentDto, oneTimeToken));
    }
}


