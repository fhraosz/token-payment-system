package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {

    private final RestTemplate restTemplate;

    public String sendPostForEntity(String endpoint, Object requestBody) {
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, requestBody, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RestClientException("Failed to communicate with external service, status code: " + response.getStatusCode());
        }
    }
}
