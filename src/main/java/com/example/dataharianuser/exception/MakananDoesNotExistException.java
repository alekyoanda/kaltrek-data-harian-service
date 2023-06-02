package com.example.dataharianuser.exception;

public class MakananDoesNotExistException extends RuntimeException {
    public MakananDoesNotExistException(Long id) {
        super("Makanan with id " + id + " does not exist");
    }
}
