package com.bulewalnut.tokenpaymentsystem.util;

import com.bulewalnut.tokenpaymentsystem.dto.ResponseDto;
import com.bulewalnut.tokenpaymentsystem.exception.RestClientException;
import com.bulewalnut.tokenpaymentsystem.type.MessageTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {

    private final RestTemplate restTemplate;

    private <T> ResponseEntity<ResponseDto<T>> exchange(String url, HttpMethod method, Object requestBody, ParameterizedTypeReference<ResponseDto<T>> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<Object> request = requestBody != null ? new HttpEntity<>(requestBody, headers) : new HttpEntity<>(headers);

            return restTemplate.exchange(url, method, request, responseType);
        } catch (RestClientException e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_EXCEPTION.getMessage(), e);
            throw new RestClientException("RestClientException: " + e.getMessage());
        } catch (Exception e) {
            log.error(MessageTypeEnum.RESPONSE_ENTITY_REST_CLIENT_EXCEPTION.getMessage(), e);
            throw new RestClientException("Exception: " + e.getMessage());
        }
    }

    // POST 요청 (Object requestBody)
    public <T> T postRequest(String url, Object requestBody) {
        ResponseEntity<ResponseDto<T>> response = exchange(
                url,
                HttpMethod.POST,
                requestBody,
                new ParameterizedTypeReference<>() {
                }
        );
        return Objects.requireNonNull(response.getBody()).getData();
    }

    // POST 요청 (ParameterizedTypeReference 사용)
    public <T> T postRequest(String url, Object requestBody, ParameterizedTypeReference<ResponseDto<T>> responseType) {
        ResponseEntity<ResponseDto<T>> response = exchange(
                url,
                HttpMethod.POST,
                requestBody,
                responseType
        );
        return Objects.requireNonNull(response.getBody()).getData();
    }

    // POST 요청 (ResponseDto 반환)
    public <T> ResponseDto<T> postRequestWithDto(String url, Object requestBody, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                responseType
        );
        return ResponseDto.setResponseDto(true, "Success", response.getBody());
    }

    // GET 요청
    public <T> T getRequest(String url) {
        ResponseEntity<ResponseDto<T>> response = exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return Objects.requireNonNull(response.getBody()).getData();
    }

    // GET 요청 (List 반환)
    public <T> List<T> getListRequest(String url) {
        ResponseEntity<ResponseDto<List<T>>> response = exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<List<T>>>() {}
        );
        return Objects.requireNonNull(response.getBody()).getData();
    }
}
