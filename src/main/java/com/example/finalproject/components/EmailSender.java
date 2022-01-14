package com.example.finalproject.components;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSender {
    void sendEmail(SimpleMailMessage email);
}
