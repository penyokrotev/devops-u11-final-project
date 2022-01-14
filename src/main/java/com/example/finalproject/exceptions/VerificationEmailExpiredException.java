package com.example.finalproject.exceptions;

public class VerificationEmailExpiredException extends RuntimeException {

    public VerificationEmailExpiredException(String string) {
        super(string);
    }
}
