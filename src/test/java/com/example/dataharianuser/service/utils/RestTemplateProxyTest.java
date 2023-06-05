package com.example.dataharianuser.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestTemplateProxyTest {
    @InjectMocks
    private RestTemplateProxy restTemplateProxy;

    @Mock
    private RestTemplate restTemplate;


    @Test
    void get_ShouldSendGetRequestWithAuthorizationHeaderAndReturnResponseEntity() {
        // Arrange
        String url = "http://localhost:8080";
        String bearerToken = "token";
        Class<String> responseType = String.class;
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Response", HttpStatus.OK);

        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.set("Authorization", bearerToken);
        HttpEntity<?> expectedRequestEntity = new HttpEntity<>(null, expectedHeaders);

        when(restTemplate.exchange(url, HttpMethod.GET, expectedRequestEntity, responseType))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> result = restTemplateProxy.get(url, bearerToken, responseType);

        // Assert
        assertEquals(expectedResponse, result);

        verify(restTemplate, atLeastOnce())
                .exchange(url, HttpMethod.GET, expectedRequestEntity, responseType);
    }
}
