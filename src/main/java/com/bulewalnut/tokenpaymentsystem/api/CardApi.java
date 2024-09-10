package com.bulewalnut.tokenpaymentsystem.api;

import com.bulewalnut.tokenpaymentsystem.dto.ApiResponse;
import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentDto;
import com.bulewalnut.tokenpaymentsystem.dto.PaymentRecordDto;
import com.bulewalnut.tokenpaymentsystem.exception.EncryptionException;
import com.bulewalnut.tokenpaymentsystem.util.EncryptionUtil;
import com.bulewalnut.tokenpaymentsystem.util.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardApi {

    private final EncryptionUtil encryptionUtil;
    private final RestClient restClient;

    public static final String REGISTER = "/register";
    public static final String SEARCH = "/search";
    public static final String TOKEN = "/token";
    public static final String APPROVE = "/approve";

    @Value("${tokenization.service.url}")
    private String tokenizationServiceUrl;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    public String encryptAndRegisterCard(CardDto cardDto) {
        CardDto encryptCardDto = encryptCardDto(cardDto);
        String url = String.format("%s%s", tokenizationServiceUrl, REGISTER);
        return restClient.postRequest(url, encryptCardDto);
    }

    public String getTokenByRefId(String refId) {
        String url = String.format("%s%s?refId=%s", tokenizationServiceUrl, TOKEN, refId);
        return restClient.getRequest(url);
    }

    public List<CardDto> findCardByUserCi(String userCi) {
        String url = String.format("%s%s?userCi=%s", tokenizationServiceUrl, SEARCH, userCi);
        return restClient.getListRequest(url, CardDto.class);
    }

    public PaymentRecordDto paymentProcessByToken(PaymentDto requestDto) {
        ParameterizedTypeReference<ApiResponse<PaymentRecordDto>> responseType =
                new ParameterizedTypeReference<>() {};
        String url = String.format("%s%s", paymentServiceUrl, APPROVE);

        return restClient.postRequest(url, requestDto, responseType);
    }

    private CardDto encryptCardDto(CardDto cardDto) {
        try {
            String encryptedCardNumber = encryptionUtil.encrypt(cardDto.getCardNumber());
            String encryptedCardExpiry = encryptionUtil.encrypt(cardDto.getCardExpiry());
            String encryptedCardCvc = encryptionUtil.encrypt(cardDto.getCardCvc());

            return CardDto.setCardDto(encryptedCardNumber, encryptedCardExpiry, encryptedCardCvc, cardDto.getCardNickName());
        } catch (Exception e) {
            log.error("암호화에 실패하였습니다. {}", e.getMessage(), e);
            throw new EncryptionException("암호화에 실패하였습니다.", e);
        }
    }

    public PaymentRecordDto findPaymentRecordByTransactionId(String transactionId) {
        ParameterizedTypeReference<ApiResponse<PaymentRecordDto>> responseType =
                new ParameterizedTypeReference<>() {};
        String url = String.format("%s%s", paymentServiceUrl, SEARCH);

        return restClient.postRequest(url, transactionId, responseType);
    }
}
