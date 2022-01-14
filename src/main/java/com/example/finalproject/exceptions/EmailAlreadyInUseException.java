package com.example.finalproject.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException(String string) {
        super(string);
    }
}