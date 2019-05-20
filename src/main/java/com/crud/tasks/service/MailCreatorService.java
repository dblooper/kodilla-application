package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    private static final String TASKS_URL = "http://localhost:8888/tasks_frontend";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks!");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", TASKS_URL);
        context.setVariable("button", "Vist Website!");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildRemindTasksStatusEmail(String message) {
        List<String> motivationSentences = new ArrayList<>();
        motivationSentences.add("Life requieres sacrifaces!");
        motivationSentences.add("If you don't make a step forward everyday, you are behind the game!");
        motivationSentences.add("Each task in done list makes you smarter!");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", TASKS_URL);
        context.setVariable("button", "See your tasks!");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("motivationS", motivationSentences);
        return templateEngine.process("mail/tasks-status-mail", context);
    }

}
