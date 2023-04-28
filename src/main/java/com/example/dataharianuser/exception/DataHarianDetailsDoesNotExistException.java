package com.example.dataharianuser.exception;

public class DataHarianDetailsDoesNotExistException extends RuntimeException {
    public DataHarianDetailsDoesNotExistException(Long id, Long dataHarianId) {
        super("Data Harian Details with id " + id + "with Data Harian id " + dataHarianId + " does not exist");
    }
}
