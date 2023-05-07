package com.example.dataharianuser.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorTemplate(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
}
