package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String postForStringResponse(String endpoint, Object requestBody) {
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, requestBody, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RestClientException("Failed to communicate with external service, status code: " + response.getStatusCode());
        }
    }

    public <T> List<T> getListFromGetResponse(String url, Class<T> dtoClass) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                return objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, dtoClass));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RestClientException("Failed to communicate with external service, status code: " + response.getStatusCode());
        }
    }
}
