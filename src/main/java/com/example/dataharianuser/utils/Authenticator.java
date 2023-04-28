package com.example.dataharianuser.utils;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class Authenticator {
    private static String url = "http://localhost:8080/api/v1/auth/get-userid";
    private static Authenticator uniqueInstance;
    private RestTemplate restTemplate;

    private Authenticator(){
        restTemplate = MyRestTemplate.getInstance();
    }

    public static Authenticator getInstance(){
        if (uniqueInstance == null){
            uniqueInstance = new Authenticator();
        }
        return uniqueInstance;
    }

    public Integer getUserId(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("",headers);
        ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.GET, entity, Integer.class);
        Integer userId = response.getBody();
        return userId;
    }

}
