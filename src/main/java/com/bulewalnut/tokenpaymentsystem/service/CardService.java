package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CardService {

    private final RestTemplate restTemplate;
    private final EncryptionUtils encryptionUtils;

    @Value("${tokenization.service.url}")
    private String tokenizationServiceUrl;

    public String sendCardDto(CardDto cardDto) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(tokenizationServiceUrl + "/register", encryptCardDto(cardDto), String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Card registration failed due to an unexpected error";
        }
    }

    private CardDto encryptCardDto(CardDto cardDto) throws Exception {

        String encryptedCardNumber = encryptionUtils.encrypt(cardDto.getCardNumber());
        String encryptedCardExpiry = encryptionUtils.encrypt(cardDto.getCardExpiry());
        String encryptedCardCvc = encryptionUtils.encrypt(cardDto.getCardCvc());

        return CardDto.of(encryptedCardNumber, encryptedCardExpiry, encryptedCardCvc);
    }

}
