package com.example.dataharianuser.service.utils;

import com.example.dataharianuser.model.dto.makanan.MakananDetailsDto;
import com.example.dataharianuser.model.dto.makanan.TypeMakananResponse;
import com.example.dataharianuser.service.makanan.MakananDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class URLManagerTest {
    @InjectMocks
    private URLManager urlManager;

    @Value("${endpoint.url2}")
    private String expectedBaseUrlMakanan;
    @Value("${endpoint.url}")
    private String expectedUrlAuth;

    @BeforeEach
    void setUp() {
        // Set the values of urlAuth and baseUrlMakanan using ReflectionTestUtils
        ReflectionTestUtils.setField(urlManager, "urlAuth", expectedUrlAuth);
        ReflectionTestUtils.setField(urlManager, "baseUrlMakanan", expectedBaseUrlMakanan);
    }

    @Test
    void testGetBaseUrlMakanan() {
        String baseUrlMakanan = urlManager.getBaseUrlMakanan();

        Assertions.assertEquals(expectedBaseUrlMakanan, baseUrlMakanan);
    }

    @Test
    void testGetUrlAuth() {
        String urlAuth = urlManager.getUrlAuth();

        Assertions.assertEquals(expectedUrlAuth, urlAuth);
    }
}
