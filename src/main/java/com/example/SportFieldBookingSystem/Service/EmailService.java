package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.Email;

public interface EmailService {

    void sendTextEmail(Email email);
    void sendHtmlEMail(Email email, String resetLink, String userName);

    void sendHtmlVeOnlineEmail(Email email);
}
