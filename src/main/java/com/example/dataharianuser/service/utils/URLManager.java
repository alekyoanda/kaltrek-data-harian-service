package com.example.dataharianuser.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLManager {
    @Value("${endpoint.url}")
    private String urlAuth;
    @Value("${endpoint.url2}")
    private String baseUrlMakanan;

    public String getBaseUrlMakanan() {
        return baseUrlMakanan;
    }

    public String getUrlAuth() {
        return urlAuth;
    }
}
