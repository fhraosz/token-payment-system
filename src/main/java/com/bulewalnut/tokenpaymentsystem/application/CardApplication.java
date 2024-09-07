package com.bulewalnut.tokenpaymentsystem.application;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardApplication {

    private final CardService cardService;

    public String encryptAndSendCardDto(CardDto cardDto) {
        return cardService.sendCardDto(cardDto);
    }
}


