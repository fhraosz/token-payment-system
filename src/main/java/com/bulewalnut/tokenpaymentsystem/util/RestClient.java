package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.ApiResponse;
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public <T> T postResponse(String url, Object requestBody) {
        try {

            ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody),
                    new ParameterizedTypeReference<>() {}
            );
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            log.error("Failed to communicate with external service ", e);
            return null;
        } catch (Exception e) {
            log.error("Card registration failed due to an unexpected error ", e);
            return null;
        }
    }

    public <T> T postResponse(String url, Object requestBody, ParameterizedTypeReference<ApiResponse<T>> responseType) {
        try {
            ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody),
                    responseType
            );
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            log.error("Failed to communicate with external service ", e);
            return null;
        } catch (Exception e) {
            log.error("Card registration failed due to an unexpected error ", e);
            return null;
        }
    }

    public <T> T getResponse(String url) {
        try {
            ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            log.error("Failed to communicate with external service ", e);
            return null;
        } catch (Exception e) {
            log.error("Card registration failed due to an unexpected error ", e);
            return null;
        }
    }

    public <T> List<T> getListResponse(String url, Class<T> dtoClass) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    ApiResponse<List<T>> apiResponse = objectMapper.readValue(response.getBody(),
                            objectMapper.getTypeFactory().constructParametricType(ApiResponse.class,
                                    objectMapper.getTypeFactory().constructCollectionType(List.class, dtoClass)));
                    return apiResponse.getData();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RestClientException("Failed to communicate with external service, status code: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            log.error("Failed to communicate with external service", e);
            return null;
        } catch (Exception e) {
            log.error("Card registration failed due to an unexpected error", e);
            return null;
        }


    }
}
