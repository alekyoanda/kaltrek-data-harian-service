package com.example.dataharianuser.service.utils;


import com.example.dataharianuser.dto.GetUserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class Authenticator {
    private static String url = "http://localhost:8080/api/v1/auth/get-user";
    private final RestTemplate restTemplate;

    public Integer getUserId(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("",headers);
        try {
            ResponseEntity<GetUserDataResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, GetUserDataResponse.class);
            System.out.println(response.getBody().getId());
            return response.getBody().getId();
        }
        catch (Exception e){
            return null;
        }
    }
}
