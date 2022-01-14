package com.example.finalproject.exceptions;

public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException(String string) {
        super(string);
    }

}

