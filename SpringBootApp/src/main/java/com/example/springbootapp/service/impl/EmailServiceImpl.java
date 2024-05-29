package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dto.EmailInfosDto;
import com.example.springbootapp.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSenderImpl mailSender;

    @Override
    public void sendOrderConfirmationEmail(EmailInfosDto emailInfos) throws MessagingException{
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        Context context=new Context();
        context.setVariable("name",emailInfos.getName());
        context.setVariable("orderId",emailInfos.getOrderId());
        context.setVariable("totalCost",emailInfos.getTotalCost());
        context.setVariable("orderDate",emailInfos.getOrderDate());
        String htmlContent=templateEngine.process("email-template",context);

        helper.setTo(emailInfos.getTo());
        helper.setSubject("Conferma ordine #"+emailInfos.getOrderId());
        helper.setText(htmlContent,true);

        mailSender.send(message);
    }
}
