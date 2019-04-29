package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldReturnAllTasks() {
        //Given
        List<Task> taskList = new ArrayList<>();
        Task task1 = new Task(1L, "Test_title_task1", "Tested_content_task1");
        Task task2 = new Task(2L, "Test_title_task2", "Tested_content_task2");
        taskList.add(task1);
        taskList.add(task2);

        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> returnedTaskList = dbService.getAllTasks();

        //When
        assertEquals(2, returnedTaskList.size());
        assertEquals("Test_title_task1", returnedTaskList.get(0).getTitle());
    }

    @Test
    public void shouldReturnNull() {
        //Given
        Optional<Task> task = Optional.empty();
        when(taskRepository.findById(1L)).thenReturn(task);

        //When&Then
        assertNotNull(dbService.getTask(1L));
    }

    @Test
    public void shouldSaveTask() {
        //Given
        Task task1 = new Task(1L, "Test_title_task1", "Tested_content_task1");
        when(taskRepository.save(task1)).thenReturn(task1);

        //When
        Task returnedTask = dbService.saveTask(task1);

        //Then
        assertNotNull(returnedTask);
        assertEquals("Tested_content_task1", returnedTask.getContent());
    }

//    @Test
//    public void shouldDeleteTask() {
//        //Given
//        List<Task> taskList = new ArrayList<>();
//        Task task1 = new Task(1L, "Test_title_task1", "Tested_content_task1");
//        Task task2 = new Task(2L, "Test_title_task2", "Tested_content_task2");
//        taskList.add(task1);
//        taskList.add(task2);
//
//        when(taskRepository.delete(task1)).then(taskList.remove(0));
//    }
}