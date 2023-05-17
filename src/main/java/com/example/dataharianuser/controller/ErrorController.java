package com.example.dataharianuser.controller;
import com.example.dataharianuser.exception.UnauthenticatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @RequestMapping("/error/unauthenticated")
    public ResponseEntity<Object> handleUnauthenticatedError() {
        throw new UnauthenticatedException();
    }
}

