package com.example.finalproject.components;

import com.example.finalproject.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationTokenIntImpl implements ConfirmationTokenInt {
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenIntImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public com.example.finalproject.models.ConfirmationToken getByConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.getByConfirmationToken(confirmationToken);
    }
}
