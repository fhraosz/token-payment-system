package com.bulewalnut.tokenpaymentsystem.service;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.exception.EncryptionException;
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.bulewalnut.tokenpaymentsystem.util.EncryptionUtil;
import com.bulewalnut.tokenpaymentsystem.util.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {

    private final EncryptionUtil encryptionUtil;
    private final RestClient restClient;

    public static final String REGISTER = "/register";

    @Value("${tokenization.service.url}")
    private String tokenizationServiceUrl;

    public String sendCardDto(CardDto cardDto) {
        try {
            CardDto encryptCardDto = encryptCardDto(cardDto);
            return restClient.sendPostForEntity(tokenizationServiceUrl + REGISTER, encryptCardDto);

        } catch (EncryptionException e) {
            log.error("Encryption failed due to an unexpected error", e);
            return null;
        } catch (RestClientException e) {
            log.error("Failed to communicate with external service", e);
            return null;
        } catch (Exception e) {
            log.error("Card registration failed due to an unexpected error", e);
            return null;
        }
    }

    private CardDto encryptCardDto(CardDto cardDto) {
        try {
            String encryptedCardNumber = encryptionUtil.encrypt(cardDto.getCardNumber());
            String encryptedCardExpiry = encryptionUtil.encrypt(cardDto.getCardExpiry());
            String encryptedCardCvc = encryptionUtil.encrypt(cardDto.getCardCvc());

            return CardDto.of(encryptedCardNumber, encryptedCardExpiry, encryptedCardCvc);
        } catch (Exception e) {
            log.error("Encryption failed.", e);
            throw new EncryptionException("Encryption failed", e);
        }
    }
}
