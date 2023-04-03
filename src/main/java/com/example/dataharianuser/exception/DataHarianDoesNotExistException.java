package com.example.dataharianuser.exception;

public class DataHarianDoesNotExistException extends RuntimeException{
    public DataHarianDoesNotExistException(Long id) {
        super("Data Harian with id " + id + " does not exist");
    }
}
