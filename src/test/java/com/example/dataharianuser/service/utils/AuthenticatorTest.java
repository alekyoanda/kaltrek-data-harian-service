package com.example.dataharianuser.service.utils;

import com.example.dataharianuser.model.dto.user.GetUserDataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.ldap.PagedResultsControl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticatorTest {
    @Mock
    private URLManager urlManager;

    @Mock
    private RestTemplateProxy restTemplateProxy;

    @InjectMocks
    private Authenticator authenticator;

    private String token;
    private Integer id;


    @BeforeEach
    void setUp() {
        token = "testToken";
        id = 2;
    }

    @Test
    void whenGetUserIdShouldReturnCorrectUserId() {
        GetUserDataResponse responseBody = new GetUserDataResponse();
        responseBody.setId(id);

        ResponseEntity<GetUserDataResponse> response = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplateProxy.get(urlManager.getUrlAuth(), token, GetUserDataResponse.class)).thenReturn(response);

        Integer result = authenticator.getUserId(token);

        assertEquals(id, result);
        verify(restTemplateProxy, atLeastOnce()).get(urlManager.getUrlAuth(), token, GetUserDataResponse.class);
    }

    @Test
    void whenGetUserIdShouldThrowException() {
        when(restTemplateProxy.get(urlManager.getUrlAuth(), token, GetUserDataResponse.class)).thenThrow(new RuntimeException());

        Integer result = authenticator.getUserId(token);

        assertNull(result);
        verify(restTemplateProxy, atLeastOnce()).get(urlManager.getUrlAuth(), token, GetUserDataResponse.class);
    }
}

