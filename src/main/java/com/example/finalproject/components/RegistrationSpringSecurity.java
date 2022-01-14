package com.example.finalproject.components;

import com.example.finalproject.models.UserReg;

public interface RegistrationSpringSecurity {

    boolean checkIfEmailIsAlreadyRegistered(String email);

    void registerUser(UserReg userReg);

    void confirmAccount(String confirmationToken);
}
