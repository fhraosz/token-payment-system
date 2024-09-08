package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardApplication {

    private final CardService cardService;

    public String encryptAndSendCardDto(CardDto cardDto) {
        return cardService.sendPostCardDto(cardDto);
    }

    public List<CardDto> findCardByUserCi() {
        return cardService.findCardByUserCi("test123");
    }

    public String paymentProcess(String cardRefId) {
        return null;
    }
}


