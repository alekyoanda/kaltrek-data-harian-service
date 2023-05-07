package com.example.dataharianuser.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(Integer userId){
        super("User with id " + userId + " is not authorized to access resource");
    }
}
