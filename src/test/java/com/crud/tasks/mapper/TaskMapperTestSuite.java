package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.trello.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //GIVEN
        TaskDto dto = new TaskDto(1L, "testTask", "testDescription");
        //WHEN
        Task testTask = taskMapper.mapToTask(dto);
        //THEN
        assertEquals(testTask.getId(), dto.getId());
        assertEquals(testTask.getTitle(), dto.getTitle());
        assertEquals(testTask.getContent(), dto.getContent());
    }

    @Test
    void testMapToTaskDto(){
        //GIVEN
        Task task = new Task(1L, "testTask", "testDescription");
        //WHEN
        TaskDto testDto = taskMapper.mapToTaskDto(task);
        //THEN
        assertEquals(testDto.getId(), task.getId());
        assertEquals(testDto.getTitle(), task.getTitle());
        assertEquals(testDto.getContent(), task.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        //GIVEN
        Task task1 = new Task(1L, "testTask1", "testDescription1");
        Task task2 = new Task(2L, "testTask2", "testDescription2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        //WHEN
        List<TaskDto> dtos = taskMapper.mapToTaskDtoList(tasks);
        //THEN
        assertEquals(dtos.size(), 2);
        assertEquals(dtos.get(1).getContent(), tasks.get(1).getContent());
        assertEquals(dtos.get(0).getId(), tasks.get(0).getId());
        assertNotEquals(dtos.get(1).getTitle(), tasks.get(0).getTitle());
    }
}
