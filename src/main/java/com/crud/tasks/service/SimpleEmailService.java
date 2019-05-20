package com.crud.tasks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(Mail mail, boolean isScheduledMessage) {

        LOGGER.info("Starting email preparation...");

        if (isScheduledMessage) {
            try {
                javaMailSender.send(createScheduledMailMessage(mail));
                LOGGER.info("Scheduled mail has been sent.");
            }catch(MailException e){
                LOGGER.error("Scheduled email has not been send: ", e.getMessage(), e);
            }
         }else {
            try {
                javaMailSender.send(createMimeMessage(mail));
                LOGGER.info("Information email has been sent.");
            } catch (MailException e) {
                LOGGER.error("Information email has not bern send: ", e.getMessage(), e);
            }
    }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator createScheduledMailMessage(Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mailCreatorService.buildRemindTasksStatusEmail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMessage(Mail mail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));

        return mailMessage;
    }

    private void mailBase(SimpleMailMessage simpleMailMessage, Mail mail) {
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
    }
}
