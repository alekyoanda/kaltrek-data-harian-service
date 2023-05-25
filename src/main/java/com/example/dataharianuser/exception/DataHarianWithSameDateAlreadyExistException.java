package com.example.dataharianuser.exception;

import java.util.Date;

public class DataHarianWithSameDateAlreadyExistException extends RuntimeException{
    public DataHarianWithSameDateAlreadyExistException(Integer userId, Date date){
        super("Data harian with date " + date + " already exist for user with id " + userId);
    }
}
