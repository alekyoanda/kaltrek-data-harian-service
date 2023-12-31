package com.example.dataharianuser.exception.advice;

import com.example.dataharianuser.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {UnauthenticatedException.class})
    public ResponseEntity<Object> userIsNotAuthenticated(Exception exception){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                unauthorized,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(baseException, unauthorized);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> userIsNotAuthorized(Exception exception){
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                forbidden,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(baseException, forbidden);
    }

    @ExceptionHandler(value={DataHarianDoesNotExistException.class,
            DataHarianWithSameDateAlreadyExistException.class,
            DataHarianDetailsDoesNotExistException.class,
            MakananDoesNotExistException.class,
            BahanOrResepMakananDoesNotExistException.class})
    public ResponseEntity<Object> badRequestError(Exception exception){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(baseException, badRequest);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> generalError(Exception exception) {
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }
}
