package com.example.dataharianuser.service.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestTemplateProxy {
    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> get(String url, String bearerToken, Class<T> responseType) {
        HttpEntity<?> requestEntity = createRequestEntity(bearerToken, null);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    private HttpEntity<?> createRequestEntity(String bearerToken, Object requestBody) {
        HttpHeaders headers = createAuthorizationHeaders(bearerToken);
        return new HttpEntity<>(requestBody, headers);
    }

    private HttpHeaders createAuthorizationHeaders(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        return headers;
    }
}
