package com.crud.tasks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(Mail mail) {

        LOGGER.info("Starting email preparation...");

        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Email has been sent.");
        }catch(MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(Mail mail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        if(mail.getToCc() != null) {
            mailBase(mailMessage, mail);
            mailMessage.setCc(mail.getToCc());
        } else {
            mailBase(mailMessage, mail);
        }
        System.out.println(mailMessage);

        return mailMessage;
    }

    private void mailBase(SimpleMailMessage simpleMailMessage, Mail mail) {
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
    }
}
