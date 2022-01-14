package com.example.finalproject.exceptions;

public class PolicyDoesntExistException extends RuntimeException {

    public PolicyDoesntExistException(String string) {
        super(string);
    }
}
