package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.Email;

public interface EmailService {

    String sendTextEmail(Email email);
    String sendHtmlEMail(Email email, String resetLink, String userName);

    String sendHtmlVeOnlineEmail(Email email);
}
