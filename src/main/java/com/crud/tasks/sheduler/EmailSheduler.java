package com.crud.tasks.sheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.Mail;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailSheduler {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInfromation() {
            simpleEmailService.send(new Mail(adminConfig.getAdminMail(),
                                        null,
                                            SUBJECT,
                                            properTaskDatabaseSizeMessage(taskRepository.count())

            ));
    }

    public String properTaskDatabaseSizeMessage(long size) {
        String baseText = "Currently in database you got: " + size;
        if(size == 1) {
            return baseText + " task";
        }else {
            return baseText + " tasks";
        }
    }
}
