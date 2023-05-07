package com.example.dataharianuser.exception;

public class UnauthenticatedException extends RuntimeException{
    public UnauthenticatedException(){
        super("User is not logged in");
    }
}
