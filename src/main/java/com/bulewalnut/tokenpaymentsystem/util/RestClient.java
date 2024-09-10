package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.ApiResponse;
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.bulewalnut.tokenpaymentsystem.type.MessageTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public <T> T postRequest(String url, Object requestBody) {
        try {

            ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody),
                    new ParameterizedTypeReference<>() {}
            );
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_EXCEPTION.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_REST_CLIENT_EXCEPTION.getMessage(), e);
            return null;
        }
    }

    public <T> T postRequest(String url, Object requestBody, ParameterizedTypeReference<ApiResponse<T>> responseType) {
        try {
            ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody),
                    responseType
            );
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_EXCEPTION.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_REST_CLIENT_EXCEPTION.getMessage(), e);
            return null;
        }
    }

    public <T> T getRequest(String url) {
        try {
            ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_EXCEPTION.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_REST_CLIENT_EXCEPTION.getMessage(), e);
            return null;
        }
    }

    public <T> List<T> getListRequest(String url, Class<T> dtoClass) {
        try {
            ResponseEntity<ApiResponse<List<T>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_EXCEPTION.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_REST_CLIENT_EXCEPTION.getMessage(), e);
            return null;
        }
    }
}
