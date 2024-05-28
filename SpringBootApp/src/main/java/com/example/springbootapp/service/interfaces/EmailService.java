package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.EmailInfosDto;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendOrderConfirmationEmail(EmailInfosDto emailInfos)  throws MessagingException;
}
