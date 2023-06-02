package com.example.dataharianuser.service.utils;


import com.example.dataharianuser.model.dto.user.GetUserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Authenticator {
    private final URLManager urlManager;
    private final RestTemplateProxy restTemplateProxy;

    public Integer getUserId(String token){
        try {
            ResponseEntity<GetUserDataResponse> response = restTemplateProxy.get(urlManager.getUrlAuth(), token, GetUserDataResponse.class);
            GetUserDataResponse responseBody = response.getBody();
            if (responseBody != null) {
                return responseBody.getId();
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }
}
