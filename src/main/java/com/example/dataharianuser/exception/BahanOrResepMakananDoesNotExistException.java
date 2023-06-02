package com.example.dataharianuser.exception;

public class BahanOrResepMakananDoesNotExistException extends RuntimeException{
    public BahanOrResepMakananDoesNotExistException(Long id) {
        super("Bahan or Resep Makanan with id " + id + " does not exist");
    }
}
