package com.bulewalnut.tokenpaymentsystem.api;

import com.bulewalnut.tokenpaymentsystem.dto.TokenRequestDto;
import com.bulewalnut.tokenpaymentsystem.util.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentApi {

    private final RestClient restClient;

    public static final String VALIDATE = "/validate";
    public static final String CHANGE_STATE = "/change/state";

    @Value("${tokenization.service.url}")
    private String tokenizationServiceUrl;

    public Boolean validateToken(TokenRequestDto requestDto) {
        return restClient.postResponse(tokenizationServiceUrl + VALIDATE, requestDto);
    }

    public Boolean changeStateToken(TokenRequestDto requestDto) {
        return restClient.postResponse(tokenizationServiceUrl + CHANGE_STATE, requestDto);
    }
}
