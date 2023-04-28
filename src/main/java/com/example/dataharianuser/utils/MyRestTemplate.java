package com.example.dataharianuser.utils;

import org.springframework.web.client.RestTemplate;

public class MyRestTemplate {
    private static RestTemplate uniqueInstance;

    private MyRestTemplate(){}

    public static RestTemplate getInstance(){
        if (uniqueInstance == null){
            uniqueInstance = new RestTemplate();
        }
        return uniqueInstance;
    }

}
