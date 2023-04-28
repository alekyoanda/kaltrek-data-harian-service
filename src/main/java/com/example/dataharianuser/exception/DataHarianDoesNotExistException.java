package com.example.dataharianuser.exception;

import java.util.Date;

public class DataHarianDoesNotExistException extends RuntimeException{
    public DataHarianDoesNotExistException(Long id) {
        super("Data Harian with id " + id + " does not exist");
    }

    public DataHarianDoesNotExistException(Date date, Integer userId) {
        super("Data Harian with date " + date.toString() + " and with user id " + userId + " does not exist");
    }
}
