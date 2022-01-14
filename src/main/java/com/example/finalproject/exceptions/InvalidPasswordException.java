package com.example.finalproject.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String string) {
        super(string);
    }
}
