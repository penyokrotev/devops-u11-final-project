package com.example.finalproject.exceptions;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String string) {
        super(string);
    }
}
