package com.example.dataharianuser.service.utils;


import com.example.dataharianuser.dto.GetUserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class Authenticator {
    @Autowired
    private URLManager urlManager;
    private final RestTemplate restTemplate;

    public Integer getUserId(String token){
        System.out.println("url: " + urlManager.getUrlAuth());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("",headers);
        try {
            ResponseEntity<GetUserDataResponse> response = restTemplate.exchange(urlManager.getUrlAuth(), HttpMethod.GET, entity, GetUserDataResponse.class);
            System.out.println(response.getBody().getId());
            return response.getBody().getId();
        }
        catch (Exception e){
            return null;
        }
    }
}
