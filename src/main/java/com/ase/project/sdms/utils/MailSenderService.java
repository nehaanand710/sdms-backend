package com.ase.project.sdms.utils;


import com.ase.project.sdms.domain.ConfirmationToken;
import com.ase.project.sdms.domain.User;
import com.ase.project.sdms.dto.MailSenderDto;
import com.ase.project.sdms.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    public static String emailSendFrom = "smartdisaster25@gmail.com";

    public void sendEmail(MailSenderDto mailSenderDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailSenderDto.getTo());
        mailMessage.setFrom(emailSendFrom);
        mailMessage.setSubject(mailSenderDto.getSubject());
        mailMessage.setText(mailSenderDto.getMessage());
        mailSender.send(mailMessage);
    }

    public void sendPasswordResetTokenMail(User user){
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        System.out.println("Sending mail..");
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getUsername());
        mail.setFrom(emailSendFrom);
        mail.setSubject("Link to Reset your Password:");
        mail.setText("To complete the password reset process, please click here: "
                + "https://sdms2.tk/api/resetPassword?token=" + confirmationToken.getConfirmationToken());

        mailSender.send(mail);

        System.out.println("Mail sent");
    }
}
