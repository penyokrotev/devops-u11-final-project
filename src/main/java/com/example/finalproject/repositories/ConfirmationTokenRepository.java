package com.example.finalproject.repositories;

import com.example.finalproject.models.ConfirmationToken;

public interface ConfirmationTokenRepository {

    void save(ConfirmationToken confirmationToken);

    ConfirmationToken getByConfirmationToken(String confirmationToken);
}
