package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuie {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void shouldReturnTask() {
        //Given
        TaskDto taskDto = new TaskDto(13L, "Test_title", "Tested_content");

        //When
        Task returnedTask = taskMapper.mapToTask(taskDto);

        //Then
        assertNotNull(returnedTask);
        assertEquals(13L, returnedTask.getId(), 0);
        assertEquals("Test_title", returnedTask.getTitle());
    }

    @Test
    public void shouldReturnTaskDto() {
        //Given
        Task task = new Task(13L, "Test_title", "Tested_content");

        //When
        TaskDto returnedTaskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertNotNull(returnedTaskDto);
        assertEquals(13L, returnedTaskDto.getId(), 0);
        assertEquals("Test_title", returnedTaskDto.getTitle());
    }

    @Test
    public void shouldReturnTaskDtoList() {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1L, "Test_title_task1", "Tested_content_task1");
        Task task2 = new Task(2L, "Test_title_task2", "Tested_content_task2");
        tasks.add(task1);
        tasks.add(task2);

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //Then
        assertEquals(2, taskDtos.size());
        assertEquals(1, taskDtos.get(0).getId(), 0);
        assertEquals("Test_title_task1", taskDtos.get(0).getTitle());
    }

    @Test
    public void shouldReturnEmptyList() {
        //Given
        List<Task> tasks = null;

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //Then
        assertNotNull(taskDtos);
        assertEquals(0, taskDtos.size());

    }
}