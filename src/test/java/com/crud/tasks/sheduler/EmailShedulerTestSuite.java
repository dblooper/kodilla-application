package com.crud.tasks.sheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.Mail;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class EmailShedulerTestSuite {

    @InjectMocks
    EmailSheduler emailSheduler;

    @Mock
    SimpleEmailService simpleEmailService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    AdminConfig adminConfig;

    @Test
    public void sendingSheduledEmailTest() {
        //Given
        when(taskRepository.count()).thenReturn(5L);
        when(adminConfig.getAdminMail()).thenReturn("testMail@test.com");
        Mail mail = new Mail("testMail@test.com",null,"Tasks: Once a day email", "Currently in database you got: 5 tasks");

        //When
        emailSheduler.sendInfromation();

        //Then
        verify(simpleEmailService, times(1)).send(mail, true);
    }

}