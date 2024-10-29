package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.Entity.Email;
import com.example.SportFieldBookingSystem.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String email_host;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public String sendTextEmail(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email.getToEmail());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMessageBody());
        simpleMailMessage.setFrom(email_host);
        try {
            javaMailSender.send(simpleMailMessage);
            return "Email sent text successfuly";
        }catch (Exception e) {
            System.out.println("Sent email fail");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendHtmlEMail(Email email, String resetLink, String userName) {
        try { // ham nay la gui html cho chuc nang quen mat khau
            // Create Thymeleaf context and add variables
            Context context = new Context();
            context.setVariable("name", userName);
            context.setVariable("resetLink", resetLink);

            // Process HTML template with Thymeleaf
            String emailContent = templateEngine.process("email/quenMatKhau", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email.getToEmail());
            mimeMessageHelper.setSubject(email.getSubject());

            // Set the processed HTML content
            mimeMessageHelper.setText(emailContent, true);
            mimeMessageHelper.setFrom(email_host);

            javaMailSender.send(mimeMessage);
            return "Email sent with Thymeleaf template successfully";
        } catch (Exception e) {
            System.out.println("Failed to send email with Thymeleaf HTML");
            throw new RuntimeException(e);
        }
    }


    @Override
    public String sendHtmlVeOnlineEmail(Email email) { // ham gui ve online -> thieu file pdf gui kem theo
        try {
            Context context = new Context(); // dat cac bien de thay doi noi dung html o day
//            context.setVariable("name", userName);
//            context.setVariable("resetLink", resetLink);

            // Process HTML template with Thymeleaf
            String emailContent = templateEngine.process("email/veOnline", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email.getToEmail());
            mimeMessageHelper.setSubject(email.getSubject());

            // Set the processed HTML content
            mimeMessageHelper.setText(emailContent, true);
            mimeMessageHelper.setFrom(email_host);

            javaMailSender.send(mimeMessage);
            return "Email sent with Thymeleaf template successfully";
        } catch (Exception e) {
            System.out.println("Failed to send email with Thymeleaf HTML");
            throw new RuntimeException(e);
        }
    }
}
