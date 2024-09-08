package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.CardDto;
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String sendPostForEntity(String endpoint, Object requestBody) {
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, requestBody, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RestClientException("Failed to communicate with external service, status code: " + response.getStatusCode());
        }
    }

    public List<CardDto> getCardListFromEndpoint(String endpoint, String userCi) {
        String url = String.format("%s?userCi=%s", endpoint, userCi);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                return objectMapper.readValue(response.getBody(), new TypeReference<List<CardDto>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RestClientException("Failed to communicate with external service, status code: " + response.getStatusCode());
        }
    }
}
